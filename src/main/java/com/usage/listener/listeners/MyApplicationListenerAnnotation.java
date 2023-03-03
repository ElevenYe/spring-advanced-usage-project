package com.usage.listener.listeners;

import com.usage.listener.events.MyApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 自定义事件监听器（使用注解的方式）
 */
@Component
public class MyApplicationListenerAnnotation {

    @EventListener
    public void onApplicationEvent(MyApplicationEvent event) {
        System.out.println("EmailApplicationListener(@EventListener) callback!!");
        System.out.println("EmailEvent(@EventListener) --> source: " + event.getSource());
        System.out.println("EmailEvent(@EventListener) --> author: " + event.getAuthor());
        System.out.println("EmailEvent(@EventListener) --> content: " + event.getContent());
        System.out.println("EmailEvent(@EventListener) --> date: " + event.getDate());
    }
}
