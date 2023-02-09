package com.usage.postProcessors.beanFactoryPostProcessors.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Customer {

    private String id;

    private String name;
}
