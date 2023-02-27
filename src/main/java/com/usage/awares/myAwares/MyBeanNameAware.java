package com.usage.awares.myAwares;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.stereotype.Component;

/**
 * 通过 BeanNameAware 实现类可以获取当前类自己的Bean的名字
 */
@Component
public class MyBeanNameAware implements BeanNameAware {

    @Override
    public void setBeanName(String s) {
        System.out.println("MyBeanNameAware ==> BeanName回调");
        System.out.println("MyBeanNameAware ==> BeanName:" + s);
        System.out.println();
    }
}
