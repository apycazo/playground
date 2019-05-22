package com.github.apycazo.playground.jerseydemo;

import com.github.apycazo.playground.jersey.BaseResourceConfig;
import com.github.apycazo.playground.jersey.JerseyServerApplication;

public class DemoApp extends BaseResourceConfig {

  public static void main(String[] args) {
    JerseyServerApplication.config(new DemoApp(), SpringConfig.class).run();
  }

  @Override
  protected void customize() {
    enableCustomJacksonJaxbProvider();
  }
}
