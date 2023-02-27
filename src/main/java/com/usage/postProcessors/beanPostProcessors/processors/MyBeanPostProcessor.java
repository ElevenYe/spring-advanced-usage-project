package com.usage.postProcessors.beanPostProcessors.processors;

import com.usage.postProcessors.beanPostProcessors.annotations.RoutingInjected;
import com.usage.postProcessors.beanPostProcessors.services.RoutingBeanProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 基本后置处理器，有两个方法，分别在Bean初始化前后调用
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 该方法在 bean 实例化完毕（且已经注入完毕），在 afterPropertiesSet 或自定义 init 方法执行之前
     * @param bean the new bean instance
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 在 afterPropertiesSet 或自定义 init 方法执行之后
     *
     * 此处使用 BeanPostProcessor 实现了 @Resource(name = "xxx") 相同功能，使用自定义注解 @RoutingInjected(value = "xxx") 可
     * 以通过指定名称的方式从 IOC 容器中取出对应的 Bean .
     *
     * @param bean the new bean instance
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetCls = bean.getClass();
        Field[] targetFld = targetCls.getDeclaredFields();
        for (Field field : targetFld) {
            //找到制定目标的注解类
            if (field.isAnnotationPresent(RoutingInjected.class)) {
                if (!field.getType().isInterface()) {
                    throw new BeanCreationException("RoutingInjected field must be declared as an interface:" + field.getName()
                            + " @Class " + targetCls.getName());
                }
                try {
                    handleRoutingInjected(field, bean, field.getType());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    private void handleRoutingInjected(Field field, Object bean, Class type) throws IllegalAccessException {
        Map<String, Object> candidates = this.applicationContext.getBeansOfType(type);
        field.setAccessible(true);
        if (candidates.size() == 1) {
            field.set(bean, candidates.values().iterator().next());
        } else if (candidates.size() == 2) {
            String injectVal = field.getAnnotation(RoutingInjected.class).value();
            Object proxy = RoutingBeanProxyFactory.createProxy(injectVal, type, candidates);
            field.set(bean, proxy);
        } else {
            throw new IllegalArgumentException("Find more than 2 beans for type: " + type);
        }
    }
}
