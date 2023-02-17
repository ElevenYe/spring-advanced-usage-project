package com.usage;

import com.usage.postProcessors.beanPostProcessors.annotations.RoutingInjected;
import com.usage.postProcessors.beanPostProcessors.interfaces.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MyBeanPostProcessorTest {

    @Resource(name = "helloServiceImpl2")
    private HelloService HelloServiceImpl2;

    @Test
    public void test() {
        HelloServiceImpl2.sayHello();
    }
}
