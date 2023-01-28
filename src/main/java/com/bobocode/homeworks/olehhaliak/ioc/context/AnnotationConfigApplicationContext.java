package com.bobocode.homeworks.olehhaliak.ioc.context;

import com.bobocode.homeworks.olehhaliak.ioc.definitionProvider.AnnotationBeanDefinitionProvider;
import com.bobocode.homeworks.olehhaliak.ioc.registry.BeanRegistry;
import java.util.Map;

public class AnnotationConfigApplicationContext implements ApplicationContext {
  private final AnnotationBeanDefinitionProvider beanDefinitionsProvider;
  private final BeanRegistry registry;

  public AnnotationConfigApplicationContext(String basePackage) {
    beanDefinitionsProvider = new AnnotationBeanDefinitionProvider(basePackage);
    registry = new BeanRegistry(beanDefinitionsProvider.readBeanDefinitions());
  }

  public <T> T getBean(Class<? super T> beanType) {
    return registry.getBeanOfType(beanType);
  }

  public <T> T getBean(String name, Class<? super T> beanType) {
    return registry.getBeanByName(name,beanType);
  }

  public <T> Map<String, T> getAllBeans(Class<T> beanType) {
    return registry.getBeansOfType(beanType);
  }
}
