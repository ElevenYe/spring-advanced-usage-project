package com.usage.awares.myAwares;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 通过实现 EnvironmentAware 接口，可以获取 Environment 对象，操作里面的外部属性配置。
 */
@Component
public class MyEnvironmentAware implements EnvironmentAware {

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("MyEnvironmentAware ==> Environment回调");
        System.out.println("MyEnvironmentAware ==> " + environment);
        System.out.println();
    }
}
