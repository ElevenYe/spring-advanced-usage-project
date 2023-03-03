package com.usage.listener.events;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件对象
 */
@Setter
@Getter
public class MyApplicationEvent extends ApplicationEvent {

    private String author;

    private String content;

    private String date;

    public MyApplicationEvent(Object source) {
        super(source);
    }

    public MyApplicationEvent(Object source, String author, String content, String date) {
        super(source);
        this.author = author;
        this.content = content;
        this.date = date;
    }
}
