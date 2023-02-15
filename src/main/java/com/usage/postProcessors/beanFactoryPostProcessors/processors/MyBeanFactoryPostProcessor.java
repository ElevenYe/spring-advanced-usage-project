package com.usage.postProcessors.beanFactoryPostProcessors.processors;

import com.usage.postProcessors.beanFactoryPostProcessors.bean.Engine;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * spring容器前置处理器
 * 在所有的bean定义已经保存加载到beanFactory，bean实例对象还没有创建出来的时候执行。
 * BeanFactoryPostProcessor是实现spring容器功能扩展的重要接口，例如修改bean属性值，实现bean动态代理等。
 * 应用场景：
 * 1、很多框架都是通过此接口实现对spring容器的扩展，例如mybatis与spring集成时，只定义了mapper接口，无实现类，
 * 但spring却可以完成自动注入。
 * 2、对敏感信息的解密处理，比如数据库连接信息加密和解密：在实际的业务开发中，在配置文件中明文配置mysql，redis的
 * 密码实际上是不安全的，需要配置加密后的密码信息。这就会产生一个需求：需要在配置文件中配置的数据库信息是加
 * 密的，但是在把密码信息注入数据源前在程序里解密处理。BeanFactoryPostProcessor正好可以解决这个问题，在真正
 * 使用到数据源去连接数据库前，读取到加密信息，进行解密处理，再用解密后的信息替换掉Spring容器中加密信息
 */
@Component
public class MyBeanFactoryPostProcessor implements EnvironmentAware, BeanFactoryPostProcessor {

    private static final String PREFIX = "MD5[";
    private static final String SUFFIX = "]";

    private ConfigurableEnvironment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

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
        // 修改bean属性值，beanFactory.getBean("mysqlInfo") 会导致 @ConfigurationProperties 属性注入失败。原因如下：
        // 1.主程启动后先进入 SpringApplication 通过 prepareEnvironment 读取配置文件(发生在Bean生命周期之前)
        // 2.前置处理器在 SpringApplication.refresh.registerBeanPostProcessors 中执行
        // 3.此处 beanFactory.getBean("mysqlInfo") 如果没有获取到 Bean 将会调用 doCreateBean 创建一个 bean
        // 4.SpringApplication.refresh.finishBeanFactoryInitialization(beanFactory)，此处将实例化单例 Bean 后，对 Bean 进行注入属性，
        //   如果单例 Bean 已存在，则不会再注入属性
        // MysqlInfo mysqlInfo = (MysqlInfo) beanFactory.getBean("mysqlInfo");
        // System.out.println("Mysql.ip: " + mysqlInfo.getIp());

        //
        MutablePropertySources propSources = environment.getPropertySources();
        StreamSupport.stream(propSources.spliterator(), false)
                .filter(ps -> ps instanceof OriginTrackedMapPropertySource)
                .collect(Collectors.toList())
                .forEach(ps -> convertPropertySource((PropertySource<LinkedHashMap<String, Object>>) ps));
        System.out.println("敏感信息加密完成.....");
    }

    /**
     * 加密相关属性
     * @param ps
     */
    private void convertPropertySource(PropertySource ps) {
        LinkedHashMap source = (LinkedHashMap) ps.getSource();
        source.forEach((k,v) -> {
            String value = String.valueOf(v);
            if (value.startsWith(PREFIX) && value.endsWith(SUFFIX)) {
                value = value.replace(PREFIX, "").replace(SUFFIX, "");
                value = md5(value);
                source.put(k, value);
            }
        });
    }

    public static String md5(String source) {
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest md 	= MessageDigest.getInstance("MD5");
            byte[] array 		= md.digest(source.getBytes("utf-8"));
            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }
}
