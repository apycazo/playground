package com.github.apycazo.playground.jersey;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

public class BaseResourceConfig extends ResourceConfig {

  public BaseResourceConfig() {
    packages(getClass().getPackageName());
    customize();
  }

  protected void customize() {
    // empty by design
  }

  @SuppressWarnings("unchecked")
  public <T extends BaseResourceConfig> T enableCustomJacksonJaxbProvider() {
    JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    provider.setMapper(objectMapper);
    register(provider);
    return (T) this;
  }
}
