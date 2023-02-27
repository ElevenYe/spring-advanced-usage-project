package com.usage.awares.myAwares;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextAware 主要用于获取容器的上下文，可以对 ApplicationContext 进行修改
 */
@Component
public class MyApplicationContextAware implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("MyApplicationContextAware ==> ApplicationContext回调");
        System.out.println("MyApplicationContextAware ==> " + applicationContext.getBean("appleBean"));
        System.out.println();
    }
}
