package com.usage.postProcessors.beanFactoryPostProcessors.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mysql")
public class Mysql {

    @Value("${mysql.ip}")
    private String ip;

    @Value("${mysql.password}")
    private String password;
}
