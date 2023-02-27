package com.usage.awares.myAwares;

import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * 通过实现 ImportAware 接口，可以获取使用 @Import 导入该类的配置类的配置信息。
 *
 * 注意：注入Spring容器的时候要采用@Import注解注入。
 */
@Component
public class MyImportAware implements ImportAware {

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        System.out.println("MyImportAware ==> Import回调");
        System.out.println("MyImportAware ==> " + importMetadata);
        System.out.println();
    }
}
