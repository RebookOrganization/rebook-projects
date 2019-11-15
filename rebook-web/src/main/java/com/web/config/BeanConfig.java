package com.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class BeanConfig {

  @Autowired
  private Environment env;

  @Bean
  WebsiteConfig getWebsiteConfig() {

    WebsiteConfig websiteConfig = new WebsiteConfig();
    websiteConfig.setTitle(env.getProperty("website.title"));
    websiteConfig.setDescription(env.getProperty("website.description"));
    websiteConfig.setAuthor(env.getProperty("website.author"));
    websiteConfig.setKeyword(env.getProperty("website.keyword"));

    return websiteConfig;
  }

  @Bean
  public EnvConfig setEnvConfig() {
    EnvConfig envConfig = new EnvConfig();
    envConfig.setEnv("dev");
    return envConfig;
  }

}
