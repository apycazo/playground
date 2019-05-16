package com.github.apycazo.playground.micronaut.rest.api;

import com.github.apycazo.playground.micronaut.rest.config.About;
import com.github.apycazo.playground.micronaut.rest.data.Outcome;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

// check: https://www.baeldung.com/micronaut
//        https://guides.micronaut.io/micronaut-http-client/guide/index.html
@MicronautTest
@SuppressWarnings("unchecked")
class SampleControllerTest {

  private Logger log = LoggerFactory.getLogger(SampleControllerTest.class);

  @Inject
  @Client("/api")
  private RxHttpClient client;

  @Test
  void timeTest() {
    HttpRequest request = HttpRequest.GET("/time");
    String body = client.toBlocking().retrieve(request);
    assertNotNull(body);
    log.info("Body: {}", body);

    Outcome<Void> outcome = client.toBlocking().retrieve(request, Argument.of(Outcome.class, Void.class));
    assertNotNull(outcome);
    assertFalse(outcome.isError());
  }

  @Test
  void echoTest() {
    String value = "test";
    HttpRequest request = HttpRequest.GET("/echo/" + value);
    Outcome<String> outcome = client.toBlocking().retrieve(request, Argument.of(Outcome.class, String.class));
    assertNotNull(outcome);
    assertFalse(outcome.isError());
    assertEquals(value, outcome.getPayload());
  }

  @Test
  void aboutTest() {
    HttpRequest request = HttpRequest.GET("/about");
    About about = client.toBlocking().retrieve(request, About.class);
    assertNotNull(about);
    assertEquals("andres picazo", about.getAuthor());
  }
}
