package com.github.apycazo.playground.spring.conditions;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(RequiredBeanCondition.class)
public @interface RequiredBean {
  Class<?> beanClass();
  boolean isFound() default true;
}
