package com.bobocode.homeworks.olehhaliak.ioc.definitionProvider;

import static java.util.stream.Collectors.toSet;

import com.bobocode.homeworks.olehhaliak.ioc.model.BeanDefinition;
import java.util.Set;
import lombok.AllArgsConstructor;
import com.bobocode.homeworks.olehhaliak.ioc.annotation.Bean;
import com.bobocode.homeworks.olehhaliak.ioc.util.StringUtil;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

@AllArgsConstructor
public class AnnotationBeanDefinitionProvider {
  private final String basePackage;

  public Set<BeanDefinition> readBeanDefinitions() {
    Reflections reflections = new Reflections(basePackage, new SubTypesScanner(false));
    Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);
    return allClasses.stream()
        .filter(aClass -> aClass.isAnnotationPresent(Bean.class))
        .map(this::annotatedClassToBeanDefinition)
        .collect(toSet());
  }

  private BeanDefinition annotatedClassToBeanDefinition(Class<?> aClass) {
    Bean beanAnnotation = aClass.getAnnotation(Bean.class);
    String beanName;
    if (!beanAnnotation.name().isEmpty()) {
      beanName = beanAnnotation.name();
    } else {
      beanName = StringUtil.initToCamelCase(aClass.getSimpleName());
    }
    return new BeanDefinition(beanName, aClass);
  }
}
