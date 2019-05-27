package com.github.apycazo.playground.spring.conditions;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(RequiredPropertyCondition.class)
public @interface RequiredProperty {
  String key();
  String value() default "";
  boolean matchOnMissingProperty() default false;
}
