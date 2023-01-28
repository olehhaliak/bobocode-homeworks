package com.bobocode.homeworks.olehhaliak.ioc.context;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bobocode.homeworks.olehhaliak.demo.ClassA;
import com.bobocode.homeworks.olehhaliak.demo.ClassB1;
import com.bobocode.homeworks.olehhaliak.demo.ClassB2;
import com.bobocode.homeworks.olehhaliak.demo.ClassB3;
import com.bobocode.homeworks.olehhaliak.demo.InterfaceA;
import com.bobocode.homeworks.olehhaliak.demo.InterfaceB;
import com.bobocode.homeworks.olehhaliak.demo.InterfaceC;
import com.bobocode.homeworks.olehhaliak.ioc.exception.NoSuchBeanException;
import com.bobocode.homeworks.olehhaliak.ioc.exception.NoUniqueBeanException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AnnotationConfigApplicationContextTest {

  AnnotationConfigApplicationContext applicationContext =
      new AnnotationConfigApplicationContext("com.bobocode.homeworks.olehhaliak.demo");

  @Nested
  class getBeanByClass {
    @Test
    void throwsNoSuchBeanIfNoCandidateFound() {
      assertThrows(NoSuchBeanException.class, () -> applicationContext.getBean(InterfaceC.class));
    }

    @Test
    void throwsNoUniqueBeanIfMultipleCandidatesFound() {
      assertThrows(NoUniqueBeanException.class, () -> applicationContext.getBean(InterfaceB.class));
    }

    @Test
    void returnBeanIfSingleCandidateFound() {
      InterfaceA beanA = applicationContext.getBean(InterfaceA.class);
      assertSame(ClassA.class, beanA.getClass());
    }
  }

  @Nested
  class getBeanByNameAndClass {
    @Test
    void throwsNoSuchBeanIfNoCandidateFound() {
      assertThrows(NoSuchBeanException.class,
          () -> applicationContext.getBean("non-existing bean", InterfaceC.class));
    }

    @Test
    void returnBeanIfCandidateFound() {
      InterfaceB beanB = applicationContext.getBean("Uniquely named bean", InterfaceB.class);
      assertSame(ClassB3.class, beanB.getClass());
    }

    @Test
    void throwsNoSuchBeanIfCandidateTypeMismatches() {
      assertThrows(NoSuchBeanException.class,
          () -> applicationContext.getBean("Uniquely named bean", InterfaceC.class));

    }

  }

  @Nested
  class getBeansByType {

    @Test
    void returnEmptyMapIfNoCandidatesFound() {
      Map<String, InterfaceC> mapOfBeans = applicationContext.getAllBeans(InterfaceC.class);
      assertTrue(mapOfBeans.isEmpty());
    }

    @Test
    void returnAllCandidatesOfMatchingType() {
      final Set<InterfaceB> expectedBeans = Set.of(new ClassB1(), new ClassB2(), new ClassB3());

      var beansFromContext = applicationContext.getAllBeans(InterfaceB.class).values();
      Set<Class<? extends InterfaceB>> beanTypes = beansFromContext.stream()
          .map(InterfaceB::getClass)
          .collect(Collectors.toSet());

      expectedBeans.forEach(
          expectedBean -> assertTrue(beanTypes.contains(expectedBean.getClass()))
      );
    }
  }
}