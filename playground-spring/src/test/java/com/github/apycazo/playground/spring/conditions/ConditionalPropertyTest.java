package com.github.apycazo.playground.spring.conditions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestPropertySource(properties = "test.prop=true")
public class ConditionalPropertyTest {

  @Autowired(required = false)
  private List<TestBean> beans;

  @Test
  void someBeanIsInjected() {
    assertThat(beans).isNotNull();
    assertThat(beans).isNotEmpty();
    assertThat(beans).hasSize(2);
  }

  @Test
  void havingValidPropertyKeyValueGeneratesBean() {
    assertThat(beans.stream().anyMatch(b -> b instanceof BeanWithValidKeyAndValue)).isTrue();
  }

  @Test
  void havingInvalidPropertyValueDoesNotGenerateBean() {
    assertThat(beans.stream().anyMatch(b -> b instanceof BeanWithValidKeyAndInvalidValue)).isFalse();
  }

  @Test
  void havingInvalidPropertyKeyAndValidValueDoesNotGenerateBean() {
    assertThat(beans.stream().anyMatch(b -> b instanceof BeanWithInvalidKeyAndValidValue)).isFalse();
  }

  @Test
  void havingValidPropertyKeyGeneratesBean() {
    assertThat(beans.stream().anyMatch(b -> b instanceof BeanRequiringOnlyValidKey)).isTrue();
  }

  @Test
  void havingInvalidPropertyKeyOnlyDoesNotGenerateBean() {
    assertThat(beans.stream().anyMatch(b -> b instanceof BeanRequiringOnlyInvalidKey)).isFalse();
  }

  // --- spring configuration

  @Configuration
  public static class SpringTestConfig {

    @Bean
    @RequiredProperty(key = "test.prop", value = "true")
    public BeanWithValidKeyAndValue beanHavingValidPropertyValue() {
      return new BeanWithValidKeyAndValue();
    }

    @Bean
    @RequiredProperty(key = "test.prop", value = "false")
    public BeanWithValidKeyAndInvalidValue beanHavingInvalidPropertyValue() {
      return new BeanWithValidKeyAndInvalidValue();
    }

    @Bean
    @RequiredProperty(key = "test.invalid", value = "true")
    public BeanWithInvalidKeyAndValidValue beanHavingInvalidKeyAndValidValue() {
      return new BeanWithInvalidKeyAndValidValue();
    }

    @Bean
    @RequiredProperty(key = "test.prop")
    public BeanRequiringOnlyValidKey beanHavingValidKey() {
      return new BeanRequiringOnlyValidKey();
    }

    @Bean
    @RequiredProperty(key = "test.invalid")
    public BeanRequiringOnlyInvalidKey beanHavingInvalidKey() {
      return new BeanRequiringOnlyInvalidKey();
    }
  }

  // --- support classes

  private static class BeanWithValidKeyAndValue extends TestBean {}
  private static class BeanWithValidKeyAndInvalidValue extends TestBean {}
  private static class BeanWithInvalidKeyAndValidValue extends TestBean {}
  private static class BeanRequiringOnlyValidKey extends TestBean {}
  private static class BeanRequiringOnlyInvalidKey extends TestBean {}

  private static class TestBean {}
}
