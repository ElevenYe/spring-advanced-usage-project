package com.usage.postProcessors.beanFactoryPostProcessors;

import com.usage.postProcessors.beanFactoryPostProcessors.bean.BenzCar;
import com.usage.postProcessors.beanFactoryPostProcessors.interfaces.Engine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean(initMethod = "start")
    BenzCar benzCar(Engine engine) {
        BenzCar car = new BenzCar();
        car.engine = engine;
        return car ;
    }
}
