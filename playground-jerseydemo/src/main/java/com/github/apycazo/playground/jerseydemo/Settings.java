package com.github.apycazo.playground.jerseydemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Settings {

  private final Logger log = LoggerFactory.getLogger(Settings.class);

  @Value("${app.test:}")
  private String testValue;
  @Value("${app.author:}")
  private String author;
  @Value("${app.version:0}")
  private int version;

  public String getTestValue() {
    return testValue;
  }

  public void setTestValue(String testValue) {
    this.testValue = testValue;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  @PostConstruct
  private void report () {
    log.info("Created settings bean");
  }
}
