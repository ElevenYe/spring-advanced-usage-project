package com.usage.postProcessors.beanFactoryPostProcessors.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mysql")
public class MysqlInfo {

    private String ip;

    private String password;
}
