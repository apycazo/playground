package com.github.apycazo.playground.jerseydemo;

import com.github.apycazo.playground.jersey.spring.SupportBeans;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@Import(SupportBeans.class)
@PropertySource("classpath:test.properties")
public class SpringConfig {
  // no beans defined here
}
