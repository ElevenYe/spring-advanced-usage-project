package com.usage;

import com.usage.postProcessors.beanFactoryPostProcessors.bean.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MyBeanDefinitionRegistryPostProcessorTest {

    @Resource
    private Company company;

    @Test
    public void companyTest() {
        System.out.println("CompanyName: " + company.getCompanyName());
        Assertions.assertEquals("阿里巴巴", company.getCompanyName(), "OK");
    }
}
