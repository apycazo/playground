package com.github.apycazo.playground.jersey.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Arrays;

@Lazy
@Configuration
public class SupportBeans {

  private static final Resource[] resourceLocations = {
    new ClassPathResource("app.properties"),
    new ClassPathResource("app.yml"),
    new ClassPathResource("application.properties"),
    new ClassPathResource("application.yml"),
    new ClassPathResource("application-prod.properties"),
    new ClassPathResource("application-prod.yml"),
    new ClassPathResource("application-dev.properties"),
    new ClassPathResource("application-dev.yml")
  };

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer () {
    PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
    Resource firstMatch = Arrays.stream(resourceLocations).filter(Resource::exists).findFirst().orElse(null);
    if (firstMatch != null && firstMatch.getFilename() != null) {
      if (firstMatch.getFilename().endsWith(".properties")) {
        // load resources as properties
        ppc.setIgnoreResourceNotFound(true);
        ppc.setLocations(resourceLocations);
        ppc.setIgnoreResourceNotFound(true);
      } else {
        // load resources as yaml
        Resource [] resources = Arrays.stream(resourceLocations).filter(Resource::exists).toArray(Resource[]::new);
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResolutionMethod(YamlProcessor.ResolutionMethod.OVERRIDE);
        factory.setResources(resources);
        factory.setSingleton(true);
        factory.afterPropertiesSet();
        ppc.setProperties(factory.getObject());
      }
    }

    return ppc;
  }

  @Bean
  public ConversionService conversionService() {
    return new DefaultConversionService();
  }

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
