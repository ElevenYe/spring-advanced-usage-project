package com.usage.awares.myAwares;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.stereotype.Component;

/**
 * 通过实现 ClassLoaderAware 接口，可以回调当前类的 ClassLoader
 */
@Component
public class MyBeanClassLoaderAware implements BeanClassLoaderAware {

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("MyBeanClassLoaderAware ==> classLoader回调");
        System.out.println("MyBeanClassLoaderAware ==> " + classLoader);
        System.out.println();
    }
}
