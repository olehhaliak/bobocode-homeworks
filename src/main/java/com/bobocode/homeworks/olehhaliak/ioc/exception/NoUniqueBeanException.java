package com.bobocode.homeworks.olehhaliak.ioc.exception;

public class NoUniqueBeanException extends RuntimeException{
  public NoUniqueBeanException() {
  }

  public NoUniqueBeanException(String message) {
    super(message);
  }
}
