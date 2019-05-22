package com.github.apycazo.playground.jersey;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SocketUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

public class JettyServer extends Server {

  private final Logger log = LoggerFactory.getLogger(JettyServer.class);
  private int portNumber;
  private AnnotationConfigWebApplicationContext context;


  private JettyServer() {
    super();
  }

  private JettyServer(int port) {
    super(port);
    this.portNumber = port;
  }

  private JettyServer(int port, String contextPath, ResourceConfig resourceConfig,
                      boolean stateless, Class<?> springConfigClass) {
    // configure server
    super(port <= 0 ? SocketUtils.findAvailableTcpPort() : port);
    ServletContextHandler servletContextHandler = stateless
      ? new ServletContextHandler(NO_SESSIONS)
      : new ServletContextHandler();
    servletContextHandler.setContextPath(contextPath);
    setHandler(servletContextHandler);
    // configure resources
    ServletHolder servletHolder = new ServletHolder(new ServletContainer(resourceConfig));
    servletHolder.setInitOrder(0);
    servletContextHandler.addServlet(servletHolder, "/*");
    // optionally configure spring
    if (springConfigClass != null) {
      context = new AnnotationConfigWebApplicationContext();
      context.registerShutdownHook();
      context.setConfigLocation(springConfigClass.getName());
      servletContextHandler.addEventListener(new ContextLoaderListener(context));
    }
  }

  public int getPortNumber() {
    return portNumber;
  }

  public WebApplicationContext getWebApplicationContext() {
    return context;
  }

  public void run() {
    try {
      start();
    } catch (Exception e) {
      log.error("Failed to start server", e);
      try {
        stop();
      } catch (Exception ex) {
        log.error("Unable to stop properly", ex);
      }
      throw new RuntimeException("Failed to start server", e);
    }
  }

  // ----------------------------------------------------
  // server builder
  // ----------------------------------------------------

  public static Builder builder(ResourceConfig resourceConfig) {
    return new Builder()
      .port(8080)
      .contextPath("/")
      .stateless(true)
      .resourceConfig(resourceConfig)
      .springConfigClass(null);
  }

  public static class Builder {

    private int port;
    private String contextPath;
    private boolean stateless;
    private ResourceConfig resourceConfig;
    private Class<?> springConfigClass;

    private Builder() {}

    public Builder port(int port) {
      this.port = port;
      return this;
    }

    public Builder contextPath(String contextPath) {
      this.contextPath = contextPath;
      return this;
    }

    public Builder stateless(boolean stateless) {
      this.stateless = stateless;
      return this;
    }

    public Builder resourceConfig(ResourceConfig resourceConfig) {
      this.resourceConfig = resourceConfig;
      return this;
    }

    public Builder springConfigClass(Class<?> springConfigClass) {
      this.springConfigClass = springConfigClass;
      return this;
    }

    public JettyServer build() {
      return new JettyServer(port, contextPath, resourceConfig, stateless, springConfigClass);
    }
  }
}
