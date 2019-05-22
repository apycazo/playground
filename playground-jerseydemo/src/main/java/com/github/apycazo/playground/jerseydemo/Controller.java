package com.github.apycazo.playground.jerseydemo;

import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class Controller {

  @Autowired
  private Settings settings;

  @GET
  public Response root() {
    return Response.ok("success").build();
  }

  @GET
  @Path("test")
  public Response testValue() {
    return Response.ok(settings.getTestValue()).build();
  }

  @GET
  @Path("settings")
  @Produces(MediaType.APPLICATION_JSON)
  public Settings getSettings() {
    return settings;
  }
}
