package com.bobocode.homeworks.olehhaliak.ioc.util;

import static java.lang.Character.toLowerCase;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class StringUtil {

  public static String initToCamelCase(String str) {
    return toLowerCase(str.charAt(0)) + str.substring(1);
  }
}
