package com.bobocode.homeworks.olehhaliak.ioc.registry;

import static java.util.stream.Collectors.toMap;

import com.bobocode.homeworks.olehhaliak.ioc.model.BeanDefinition;
import com.bobocode.homeworks.olehhaliak.ioc.exception.BeanCreationException;
import com.bobocode.homeworks.olehhaliak.ioc.exception.NoSuchBeanException;
import com.bobocode.homeworks.olehhaliak.ioc.exception.NoUniqueBeanException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BeanRegistry {

  Map<String, ?> beansByName = new HashMap<>();

  public BeanRegistry(Set<BeanDefinition> beanDefinitions) {
    init(beanDefinitions);
  }

  public <T> T getBeanByName(String beanName, Class<? super T> beanType) {
    return (T) Optional.ofNullable(beansByName.get(beanName))
        .filter(bean -> beanType.isAssignableFrom(bean.getClass()))
        .orElseThrow(NoSuchBeanException::new);
  }

  public <T> Map<String, T> getBeansOfType(Class<? super T> beanType) {
    return beansByName.entrySet().stream()
        .filter(e -> beanType.isAssignableFrom(e.getValue().getClass()))
        .collect(toMap(Map.Entry::getKey, e -> (T) e.getValue()));
  }

  public <T> T getBeanOfType(Class<? super T> beanType) {
    Map<String, T> candidates = getBeansOfType(beanType);
    if (candidates.size() > 1) {
      throw new NoUniqueBeanException();
    }
    return candidates.values().stream()
        .findFirst()
        .orElseThrow(NoSuchBeanException::new);
  }

  private void init(Set<BeanDefinition> beanDefinitions) {
    beansByName = beanDefinitions.stream()
        .collect(toMap(BeanDefinition::beanName, this::getBeanInstance));
  }

  private Object getBeanInstance(BeanDefinition beanDefinition) {
    try {
      return beanDefinition.type().getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new BeanCreationException(
          "Failed to create an instance of bean " + beanDefinition.beanName());
    }
  }

}
