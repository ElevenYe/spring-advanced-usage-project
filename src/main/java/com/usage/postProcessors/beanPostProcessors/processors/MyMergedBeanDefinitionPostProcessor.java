package com.usage.postProcessors.beanPostProcessors.processors;

import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * 在创建好bean实例对象之后会调用一下 MergedBeanDefinitionPostProcessor 的 postProcessMergedBeanDefinition 方法让用户定义一下 BeanDefinition
 */
@Component
public class MyMergedBeanDefinitionPostProcessor implements MergedBeanDefinitionPostProcessor {

    /**
     * 这个算是将 merged BeanDefinition 暴露出来的一个回调
     * 在 bean 实例化完毕后调用 可以用来修改 merged BeanDefinition 的一些 properties 或者用来给后续回调中缓存一些 meta 信息使用
     * @param beanDefinition
     * @param beanType
     * @param beanName
     */
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

    }

    @Override
    public void resetBeanDefinition(String beanName) {
        MergedBeanDefinitionPostProcessor.super.resetBeanDefinition(beanName);
    }
}
