package com.usage.awares.myAwares;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

/**
 * BeanFactoryAware 主要用于获取 BeanFactory 示例，进而获取Bean。
 */
@Component
public class MyBeanFactoryAware implements BeanFactoryAware {

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryAware ==> BeanFactory回调");
        System.out.println("MyBeanFactoryAware ==> " + beanFactory);
        System.out.println();
    }
}
