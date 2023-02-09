package com.usage.postProcessors.beanFactoryPostProcessors.processors;

import com.usage.postProcessors.beanFactoryPostProcessors.bean.Engine;
import com.usage.postProcessors.beanFactoryPostProcessors.bean.MysqlInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * spring前置处理器
 * 在所有的bean定义已经保存加载到beanFactory，bean实例对象还没有创建出来的时候执行。
 * BeanFactoryPostProcessor是实现spring容器功能扩展的重要接口，例如修改bean属性值，实现bean动态代理等。
 * 应用场景：
 * 1、很多框架都是通过此接口实现对spring容器的扩展，例如mybatis与spring集成时，只定义了mapper接口，无实现类，
 * 但spring却可以完成自动注入。
 * 2、对敏感信息的解密处理，比如数据库连接信息加密和解密：在实际的业务开发中，在配置文件中明文配置mysq，redis的
 * 密码实际上是不安全的，需要配置加密后的密码信息，但是把加密后的密码信息注入的数据源中，去连接mysql数据库肯定
 * 会连接异常，因为mysql并不知道你的加密方式和加密方法。这就会产生一个需求：需要在配置文件中配置的数据库信息是加
 * 密的，但是在把密码信息注入数据源前在程序里解密处理。BeanFactoryPostProcessor正好可以解决这个问题，在真正
 * 使用到数据源去连接数据库前，读取到加密信息，进行解密处理，再用解密后的信息替换掉Spring容器中加密信息
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 基本用法
//        baseUsage(beanFactory);
        // 接口代理用法
        proxyUsage(beanFactory);
        // 配置文件重要信息加解密用法
        decodeUsage(beanFactory);
    }

    /**
     * Engine是一个接口，一般情况下，需要在App类中配置一个Engine的实现类bean才行，否则因为缺少Engine实例，spring启动时会报错。
     * 通过FactoryBean和动态代理，可以生成Engine接口的代理对象；结合BeanFactoryPostProcessor接口，将FactoryBean动态添加
     * 到BeanFactory中，即可以给BenzCar配置上Engine接口代理对象。
     * 执行顺序 ：  BeanFactoryPostProcessor ---> 普通Bean构造方法 ---> 设置依赖或属性 ---> @PostConstruct ---> InitializingBean ---> initMethod 。
     * @param beanFactory
     *
     * -- EngineFactory 调用 getObject()方法生产 Engine代理对象
     * EngineFactory to build Engine01, EngineFactory: engine01-gbd
     *
     * -- BenzCar调用构造方法，此时 engine属性还未被设置。
     * BenzCar Constructor
     * BenzCar's engine not setting
     *
     * -- BenzCar调用@PostConstruct注解的方法，此时engine属性已经设置。
     * BenzCar postConstruct
     * BenzCar's engine installed, in postConstruct
     *
     * -- BenzCar调用 InitializingBean接口方法。
     * BenzCar initializingBean after propertieSet
     * BenzCar's engine installed, in initializingBean
     * here is invoke engine: fire
     *
     * -- BenzCar调用 initMethod指定的方法，
     * BenzCar start
     *
     * -- BenzCar调用了代理对象的方法，SpecialBeanForEngine 类中第44行代码。
     * here is invoke engine: fire
     */
    public void proxyUsage(ConfigurableListableBeanFactory beanFactory) {
        BeanDefinitionRegistry bdr = (BeanDefinitionRegistry) beanFactory;
        GenericBeanDefinition gbd = new GenericBeanDefinition();
        gbd.setBeanClass(EngineFactory.class);
        gbd.setScope(BeanDefinition.SCOPE_SINGLETON);
        gbd.setAutowireCandidate(true);
        bdr.registerBeanDefinition("engine01-gbd", gbd);
    }

    public static class EngineFactory implements FactoryBean<Engine>, BeanNameAware, InvocationHandler {

        String name;

        @Override
        public Engine getObject() {
            System.out.println("EngineFactory to build Engine01, EngineFactory: "+ name);
            Engine prox = (Engine) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{Engine.class}, this);
            return prox;
        }

        @Override
        public Class<?> getObjectType() {
            return Engine.class;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }

        @Override
        public void setBeanName(String name) {
            this.name = name;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            System.out.println("here is invoke engine: "+method.getName());
            return null;
        }
    }

    /**
     * 基础用法
     * @param beanFactory
     */
    private void baseUsage(ConfigurableListableBeanFactory beanFactory) {
        // 修改bean属性值
        ScannedGenericBeanDefinition dog = ((ScannedGenericBeanDefinition) beanFactory.getBeanDefinition("customer"))  ;
        MutablePropertyValues propertyValues = dog.getPropertyValues();
        propertyValues.addPropertyValue("name", "狗蛋儿");

        // Bean后置处理器数量
        System.out.println("");
        System.out.println("==========BeanPostProcessorCount start=========");
        System.out.println("BeanPostProcessorCount: " + beanFactory.getBeanPostProcessorCount());
        System.out.println("==========BeanPostProcessorCount end=========");

        // 遍历BeanDefinitionNames
        System.out.println("");
        System.out.println("==========beanDefinitionNames start=========");
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }
        System.out.println("==========beanDefinitionNames end=========");

        // 取customer bean的BeanDefinition信息
        System.out.println("");
        System.out.println("==========BeanDefinition start=========");
        BeanDefinition customerBeanDefinition = beanFactory.getBeanDefinition("customer");
        System.out.println("BeanClassName: " + customerBeanDefinition.getBeanClassName());
        System.out.println("FactoryBeanName: " + customerBeanDefinition.getFactoryBeanName());
        System.out.println("FactoryMethodName: " + customerBeanDefinition.getFactoryMethodName());
        System.out.println("Description: " + customerBeanDefinition.getDescription());
        // 默认为 singleton （单例）
        System.out.println("Scope: " + customerBeanDefinition.getScope());
        System.out.println("==========BeanDefinition end=========");

        // 修改Bean后置处理器类型为 prototype （多例）
        System.out.println("");
        System.out.println("==========Scope start=========");
        customerBeanDefinition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
        System.out.println("Scope: " + customerBeanDefinition.getScope());
        System.out.println("==========Scope end=========");

        System.out.println("");
    }

    /**
     * 配置文件解密
     * @param beanFactory
     */
    private void decodeUsage(ConfigurableListableBeanFactory beanFactory) {
        // 修改bean属性值  ######## 这里 beanFactory.getBean("mysqlInfo") 会让 @ConfigurationProperties 和 @Value 失效，原因不明，需要查看源码 ######
//        MysqlInfo mysqlInfo = (MysqlInfo) beanFactory.getBean("mysqlInfo");
//        System.out.println("Mysql.ip: " + mysqlInfo.getIp());
    }
}
