package com.usage.postProcessors.beanPostProcessors.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 关于处理bean被销毁的前置回调
 * 应用实例：
 *    ApplicationListenerDetector，这个类是用来注册 ApplicationListener 实例的，而如果销毁一个对象，不接触这里的引用会导致无法进行回收，
 *    因此在销毁对象时，会判断如果是 ApplicationListener 要执行从监听器列表中移除掉。
 */
@Component
public class MyDestructionAwareBeanPostProcessor implements DestructionAwareBeanPostProcessor {

    /**
     * 这里实现销毁对象的逻辑
     * @param bean
     * @param beanName
     * @throws BeansException
     */
    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {

    }

    /**
     * 判断是否需要处理这个对象的销毁
     * @param bean
     * @return
     */
    @Override
    public boolean requiresDestruction(Object bean) {
        return DestructionAwareBeanPostProcessor.super.requiresDestruction(bean);
    }
}
