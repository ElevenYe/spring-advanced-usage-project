package postProcessors.beanFactoryPostProcessors.processors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import postProcessors.beanFactoryPostProcessors.bean.Customer;

/**
 * BeanFactory后置处理器
 *
 */
public class myBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Customer customer = (Customer) beanFactory.getBean("customer");
        customer.setId("C1");
        customer.setName("高进");
    }
}
