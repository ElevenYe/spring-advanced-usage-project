package com.usage.listener.listeners;

import com.usage.listener.events.MyApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 自定义事件监听器
 */
@Component
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {
    @Override
    public void onApplicationEvent(MyApplicationEvent event) {
        System.out.println("EmailApplicationListener callback!!");
        System.out.println("EmailEvent --> source: " + event.getSource());
        System.out.println("EmailEvent --> author: " + event.getAuthor());
        System.out.println("EmailEvent --> content: " + event.getContent());
        System.out.println("EmailEvent --> date: " + event.getDate());
    }
}
