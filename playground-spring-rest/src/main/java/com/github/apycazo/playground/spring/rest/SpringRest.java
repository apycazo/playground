package com.github.apycazo.playground.spring.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SpringRest implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(SpringRest.class);
  }
}
