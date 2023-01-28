package com.bobocode.homeworks.olehhaliak.ioc.exception;

public class NoSuchBeanException extends RuntimeException {
  public NoSuchBeanException() {
  }

  public NoSuchBeanException(String message) {
    super(message);
  }
}
