package com.usage.awares.bean;

import com.usage.awares.myAwares.MyImportAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyImportAware.class)
public class ConfigImportAware {
}
