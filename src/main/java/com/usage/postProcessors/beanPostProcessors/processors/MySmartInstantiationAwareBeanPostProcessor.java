package com.usage.postProcessors.beanPostProcessors.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

@Component
public class MySmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    /**
     * 预测 InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation 方法返回的类型
     * @param beanClass the raw class of the bean
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * 确定该bd的构造函数，找不到用默认的构造函数
     * @param beanClass the raw class of the bean (never {@code null})
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName)
            throws BeansException {

        return null;
    }

    /**
     * 在需要获取earlyBean时，在返回earlyBean前对earlyBean进行后置处理。
     * @param bean the raw bean instance
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
