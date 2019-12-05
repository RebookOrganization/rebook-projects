package com.web.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebHttpSessionListener implements HttpSessionListener {

  @Value("${server.servlet.session.timeout}")
  private int timeout;

  @Override
  public void sessionCreated(HttpSessionEvent se) {
    se.getSession().setMaxInactiveInterval(timeout);
  }

}
