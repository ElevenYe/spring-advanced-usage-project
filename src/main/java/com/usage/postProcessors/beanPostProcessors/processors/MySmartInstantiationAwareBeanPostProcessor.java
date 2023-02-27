package com.usage.postProcessors.beanPostProcessors.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

/**
 * 这个接口主要是spring框架内部来使用
 */
@Component
public class MySmartInstantiationAwareBeanPostProcessor implements SmartInstantiationAwareBeanPostProcessor {

    /**
     * 预测 Bean 的类型，返回第一个预测成功的 Class 类型，如果不能预测返回 null ；当你调用 BeanFactory.getType(name) 时当通过 Bean 定
     * 义无法得到 Bean 类型信息时就调用该回调方法来决定类型信息；BeanFactory.isTypeMatch(name, targetType) 用于检测给定名字的 Bean 是
     * 否匹配目标类型（如在依赖注入时需要使用）。
     *
     * predictBeanType：该触发点发生在 postProcessBeforeInstantiation 之前，这个方法用于预测 Bean 的类型，返回第一个预测成功的 Class
     * 类型，如果不能预测返回 null；当你调用 BeanFactory.getType(name) 时当通过 bean 的名字无法得到bean类型信息时就调用该回调方法来决定
     * 类型信息。
     * @param beanClass the raw class of the bean
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public Class<?> predictBeanType(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    /**
     * 检测 Bean 的构造器，可以检测出多个候选构造器，再有相应的策略决定使用哪一个，如 AutowiredAnnotationBeanPostProcessor 实现将自动扫
     * 描通过 @Autowired/@Value 注解的构造器从而可以完成构造器注入
     *
     * 执行时机是: 对象实例化之前执行。
     *
     * determineCandidateConstructors：该触发点发生在 postProcessBeforeInstantiation 之后，用于确定该 bean 的构造函数之用，返回的
     * 是该 bean 的所有构造函数列表。用户可以扩展这个点，来自定义选择相应的构造器来实例化这个 bean。
     *
     * 使用场景：当一个 bean 中有两个构造方法的时候，一个无参构造方法，一个有参构造方法，那么 spring 在进行 bean 初始化的时候回默认调用无参的
     * 构造方法
     *
     * Spring推断构造方法的过程：在这个过程中，如果推断出有一个构造方法加了 @Autowired 注解，那么 spring 会把它放到一个临时变量当中，在判断
     * 临时变量是否为空，如果不为空，则把这个变量转换成临时数组返回出去，而如果构造方法都没有加 @Autowired 注解，那么 spring 就无法判断要把哪
     * 个加入到临时变量中，所以最后返回一个 null，然后 spring 根据返回的 null 来使用默认的构造方法。
     * @param beanClass beanClass参数表示目标实例的类型
     * @param beanName beanName是目标实例在Spring容器中的name
     * @return 返回值是个构造器数组，如果返回null，会执行下一个 PostProcessor 的 determineCandidateConstructors 方法；否则选取该
     *         PostProcessor选择的构造器
     * @throws BeansException
     */
    @Override
    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName)
            throws BeansException {

        return null;
    }

    /**
     * 获得提前暴露的bean引用，主要用于解决循环引用的问题。(只有单例对象才会调用此方法)
     *
     * 该触发点发生在postProcessAfterInstantiation之后，当有循环依赖的场景，当bean实例化好之后，为了防止有循环依赖，会提前暴露回调方法，用
     * 于bean实例化的后置处理。这个方法就是在提前暴露的回调方法中触发。
     * @param bean the raw bean instance
     * @param beanName the name of the bean
     * @return
     * @throws BeansException
     */
    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
