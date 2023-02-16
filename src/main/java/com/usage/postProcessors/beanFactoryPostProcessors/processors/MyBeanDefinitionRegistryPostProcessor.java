package com.usage.postProcessors.beanFactoryPostProcessors.processors;

import com.usage.postProcessors.beanFactoryPostProcessors.bean.Company;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * BeanDefinitionRegistryPostProcessor 可以让我们在 BeanDefinition 中添加一些自定义操作，或者将自定义的 BeanDefinition 添加到 Spring 容器中
 *
 * 执行时机： 所有的 bean 定义信息，即将要被加载到 IOC 容器中(其实还没有被加载到容器中)， bean 实例还没有被初始化时， BeanDefinitionRegistryPostProcessor 被调用。
 */
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) registry;
        // 手动将 Company Bean 注入到 Spring 容器中
        beanFactory.registerBeanDefinition("company", new RootBeanDefinition(Company.class));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // Company 设置属性值
        RootBeanDefinition company = ((RootBeanDefinition) beanFactory.getBeanDefinition("company"))  ;
        MutablePropertyValues propertyValues = company.getPropertyValues();
        propertyValues.addPropertyValue("companyCode", "COM202302160001");
        propertyValues.addPropertyValue("companyName", "阿里巴巴");
    }
}
