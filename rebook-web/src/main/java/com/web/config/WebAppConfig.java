package com.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebAppConfig {

  private final Logger logger = LoggerFactory.getLogger(WebAppConfig.class);
  private static WebAppConfig instance = new WebAppConfig();

  public static WebAppConfig getInstance() { return instance; }

  public WebAppConfig() {
  }

  public static String timeoutMiliseconds;
  private String uploadDir;
  public static String esNewsScrollApiUrl;
  public static String esNewsItemSearchUrl;

  public String getUploadDir() { return uploadDir; }

  @Value("${file.upload-dir}")
  public void setUploadDir(String uploadDir) { this.uploadDir = uploadDir; }

  @Value("${es.newsScrollApiUrl}")
  public void setEsNewsScrollApiUrl(String esNewsScrollApiUrl) {
    WebAppConfig.esNewsScrollApiUrl = esNewsScrollApiUrl;
  }

  @Value("${apis2call.timeoutMiliseconds}")
  public void setTimeoutMiliseconds(String timeoutMiliseconds) {
    WebAppConfig.timeoutMiliseconds = timeoutMiliseconds;
  }

  @Value("${es.newsItemSearchUrl}")
  public void setEsNewsItemSearchUrl(String esNewsItemSearchUrl) {
    WebAppConfig.esNewsItemSearchUrl = esNewsItemSearchUrl;
  }

}
