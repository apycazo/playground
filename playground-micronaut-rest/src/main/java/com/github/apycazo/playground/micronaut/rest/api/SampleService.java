package com.github.apycazo.playground.micronaut.rest.api;

import com.github.apycazo.playground.micronaut.rest.data.Outcome;

import javax.inject.Singleton;

@Singleton
public class SampleService {

  public Outcome<String> echo(String value) {
    return Outcome.<String>builder().payload(value).build();
  }
}
