package com.usage.postProcessors.beanFactoryPostProcessors.bean;

import lombok.Data;

/**
 * 该类没有添加 Spring 注入的注解，我们将使用 Spring 前置处理器 BeanDefinitionRegistryPostProcessor 提供的方法把该类注入至 Spring 容器中
 */
@Data
public class Company {

    private String companyCode;

    private String companyName;
}
