package com.web.config;

public class EnvConfig {
  private String env;
  private boolean isDevEnv;

  public String getEnv() { return env; }

  public void setEnv(String env) { this.env = env; }

  public boolean isDevEnv() { return isDevEnv; }

  public void setDevEnv(boolean devEnv) { isDevEnv = devEnv; }
}
