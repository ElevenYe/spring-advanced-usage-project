package com.usage.postProcessors.beanPostProcessors.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RoutingInjected {
    String value() default "helloServiceImpl1";
}
