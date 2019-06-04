package com.github.apycazo.playground.snippets;

import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class OpenFeign {

  public static void main(String[] args) {
    HttpBinClient client = Feign.builder()
      .client(new OkHttpClient())
      .encoder(new JacksonEncoder())
      .decoder(new JacksonDecoder())
      .requestInterceptor(new RequestCustomizer())
      .target(HttpBinClient.class, "http://httpbin.org");
    log.info("Request response: {}", client.get());
    log.info("Request response: {}", client.getWithParam("eureka!"));
    log.info("Request response: {}", client.getWithCustomHeader(101));
    log.info("Request response: {}", client.post("eureka"));
    log.info("Request response: {}", client.postWithBody(Arrays.asList("value1","value2")));
  }

  public interface HttpBinClient {

    @RequestLine("GET /get")
    Map<String,Object> get();

    @RequestLine("GET /anything/{p}")
    Map<String,Object> getWithParam(@Param("p") String param);

    @RequestLine("GET /get")
    @Headers("X-Api-Key: {key}")
    Map<String,Object> getWithCustomHeader(@Param(value = "key", expander = CustomExpander.class) int key);

    @RequestLine("POST /post")
    @Headers("Content-Type: application/json")
    @Body("%7B\"value\":\"{value}\"%7D")
    Map<String,Object> post(@Param("value") String value);

    @RequestLine("POST /post")
    @Headers("Content-Type: application/json")
    Map<String, Object> postWithBody(List<String> body);
  }

  public static class RequestCustomizer implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
      template.header("test", "true");
    }
  }

  public static class CustomExpander implements Param.Expander {

    @Override
    public String expand(Object value) {
      return "key-"+value+"-z";
    }
  }
}