package com.web.config;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
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

  private String tokenSecret;
  private long tokenExpirationMsec;
  private List<String> authorizedRedirectUris = new ArrayList<>();
  private String uploadDir;
  public static String authorizedRedirectUri;

//  public String getTokenSecret() { return tokenSecret; }
//
//  @Value("${auth.tokenSecret}")
//  public void setTokenSecret(String tokenSecret) { this.tokenSecret = tokenSecret; }
//
//  public long getTokenExpirationMsec() { return tokenExpirationMsec; }
//
//  @Value("${auth.tokenExpirationMsec}")
//  public void setTokenExpirationMsec(long tokenExpirationMsec) { this.tokenExpirationMsec = tokenExpirationMsec; }
//
//  public List<String> getAuthorizedRedirectUris() { return authorizedRedirectUris; }
//
//  @Value("{'${oauth2.authorizedRedirectUris}'.split(',')}")
//  public void setAuthorizedRedirectUris(List<String> authorizedRedirectUris) {
//    this.authorizedRedirectUris = authorizedRedirectUris;
//  }
//
//  @Value("${oauth2.authorizedRedirectUri}")
//  public void setAuthorizedRedirectUri(@NotBlank String authorizedRedirectUri) {
//    WebAppConfig.authorizedRedirectUri = authorizedRedirectUri;
//  }

  public String getUploadDir() { return uploadDir; }

  @Value("${file.upload-dir}")
  public void setUploadDir(String uploadDir) { this.uploadDir = uploadDir; }
}
