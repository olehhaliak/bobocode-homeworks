package com.bobocode.homeworks.olehhaliak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApp {

  public static void main(String[] args) {
    SpringApplication.run(DemoApp.class, args);
  }

  @Bean
  RestTemplate restTemplate(){
    return new RestTemplate();
  }

}
