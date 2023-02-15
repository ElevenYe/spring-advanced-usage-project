package com.usage.postProcessors.beanFactoryPostProcessors.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 绑定前缀规则：只能使用纯小写、数字、下划线作为合法字符
 * @Value 不支持松散绑定
 * @ConfigurationProperties 绑定属性支持属性名字松散绑定（即不区分大小写，忽略下划线和中横线）
 *
 * 注意：SpringBoot 2.x 之后的版本 ConfigurationProperties 的 prefix 不支持驼峰命名和下划线，支持且推荐使用"-"（烤肉串模式）
 */
@Data
@Component
@ConfigurationProperties(prefix = "mysql-service")
public class MysqlInfo {

    private String ip;

    private String password;
}
