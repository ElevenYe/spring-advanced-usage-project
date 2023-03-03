package com.usage;

import com.usage.listener.events.MyApplicationEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class MyApplicationListenerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void listenerTest() {
        applicationContext.publishEvent(
                new MyApplicationEvent(
                    "Spring监听",
                    "spring-advanced-usage-project",
                    "MyApplicationListener监听测试",
                    "2023-03-03"));
    }
}
