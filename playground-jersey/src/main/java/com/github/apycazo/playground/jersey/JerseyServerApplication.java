package com.github.apycazo.playground.jersey;

import org.glassfish.jersey.server.ResourceConfig;

public class JerseyServerApplication {

  public static JettyServer config(ResourceConfig resourceConfig) {
    return config(resourceConfig, null);
  }

  public static JettyServer config(ResourceConfig resourceConfig, Class<?> springClass) {
    return JettyServer.builder(resourceConfig).springConfigClass(springClass).build();
  }
}
