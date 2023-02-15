package com.usage.postProcessors.beanFactoryPostProcessors.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取自定义配置文件
 *
 * 方法1： @ConfigurationProperties + @PropertySource的方式只能读取以properties为后缀的配置文件
 * ·@PropertySource(value = "classpath:application-mysql1.properties")
 * ·@ConfigurationProperties(prefix = "mysql-service")
 *
 * 方法2: 主配置文件application.yml 增加 spring.profiles.include: -mysql 将自定义配置文件包含至主配置文件中
 * ·@ConfigurationProperties(prefix = "mysql-yml")
 */
@ConfigurationProperties(prefix = "mysql-yml")
@Data
@Component
public class MysqlServiceInfo {

    private String ip;

    private String password;
}
