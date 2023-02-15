package com.usage;

import com.usage.postProcessors.beanFactoryPostProcessors.bean.Customer;
import com.usage.postProcessors.beanFactoryPostProcessors.bean.MysqlInfo;
import com.usage.postProcessors.beanFactoryPostProcessors.bean.MysqlServiceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBeanFactoryPostProcessorTest {

    @Autowired
    Customer customer;

    @Autowired
    MysqlInfo mysqlInfo;

    @Autowired
    MysqlServiceInfo mysqlServiceInfo;

    @Test
    public void MysqlInfo() {
        System.out.println(customer.getName());
        System.out.println("mysqlInfo: " + mysqlInfo.getPassword()
                + "  mysqlServiceInfo: " + mysqlServiceInfo.getPassword());
    }
}
