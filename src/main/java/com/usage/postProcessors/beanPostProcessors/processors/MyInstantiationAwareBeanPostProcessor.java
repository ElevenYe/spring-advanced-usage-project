package com.usage.postProcessors.beanPostProcessors.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 实例化后置处理器
 * 在spring容器创建对象之前，会先调用 InstantiationAwareBeanPostProcessor 的 postProcessBeforeInstantiation 方法尝试返回对象，如果
 * 用户返回了则会用用户创建的对象。 如果没有则调用 doCreateBean 方法，方法内也会从一个缓存中获取，没有的话就继续调用方法创建 创建逻辑会让用户返
 * 回一个构造函数，注意这里调用的是 SmartInstantiationAwareBeanPostProcessor 的一个方法，如果没有就用默认的无参构造函数，之后就是上面写过
 * 的用工具类创建的方式
 */
@Component
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    /**
     * 在最开始调用，如果返回Bean实例不为空，直接调用BeanPostProcessor的postProcessAfterInitialization方法，返回该bean，不在进行其他动作。
     * @param beanClass the class of the bean to be instantiated
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * 判断是否需要进行属性填充
     * @param bean the bean instance created, with properties not having been set yet
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * 进行属性填充前，处理bean的PropertyValues。用于属性填充
     * @param pvs the property values that the factory is about to apply (never {@code null})
     * @param bean the bean instance created, but whose properties have not yet been set
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {

        return null;
    }
}
