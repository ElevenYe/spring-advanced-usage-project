package com.usage;

import com.usage.postProcessors.beanFactoryPostProcessors.bean.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBeanDefinitionRegistryPostProcessorTest {

    @Autowired
    private Company company;

    @Test
    public void companyTest() {
        System.out.println("CompanyName: " + company.getCompanyName());
        Assertions.assertEquals("阿里巴巴", company.getCompanyName());
    }
}
