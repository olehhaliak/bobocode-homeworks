package com.bobocode.homeworks.olehhaliak.ioc.context;


import java.util.Map;

public interface ApplicationContext {
  <T> T getBean(Class<? super T> beanType);

  <T> T getBean(String name, Class<? super T> beanType);

  <T> Map<String,T> getAllBeans(Class<T> beanType);

}
