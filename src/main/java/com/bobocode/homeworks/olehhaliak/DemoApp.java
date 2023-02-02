package com.bobocode.homeworks.olehhaliak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class DemoApp {

  public static void main(String[] args) {
    SpringApplication.run(DemoApp.class, args);
  }

  @Bean
  RestTemplate restTemplate(){
    return new RestTemplate();
  }

}
