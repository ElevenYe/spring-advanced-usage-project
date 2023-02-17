package com.usage.postProcessors.beanPostProcessors.services;

import com.usage.postProcessors.beanPostProcessors.interfaces.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl2 implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("你好我是HelloServiceImpl2");
    }
}
