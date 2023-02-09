package com.usage;

import com.usage.postProcessors.beanFactoryPostProcessors.bean.Customer;
import com.usage.postProcessors.beanFactoryPostProcessors.bean.MysqlInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBeanFactoryPostProcessorTest {

    @Autowired
    MysqlInfo mysqlInfo;

    @Autowired
    Customer customer;

    @Test
    public void MysqlInfo() {
        System.out.println(customer.getName());
        System.out.println(mysqlInfo.getIp());
    }
}
