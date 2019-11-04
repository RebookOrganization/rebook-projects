package com.web.utils;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

public class SigUtils {
  private static final Logger logger = LoggerFactory.getLogger("SigUtils");
  private StringBuilder sig = null;

  public SigUtils addSig(String value) {
    if (StringUtils.isBlank(value)) {
      value = "";
    }
    if(Objects.isNull(sig)) {
      sig = new StringBuilder();
      sig.append(value);
      return this;
    }
    sig.append("|").append(value);
    return this;
  }

  public String buildSHA256Base64() {
    logger.info("sig for build: " + sig);
    String sigBuilt ="";
    try {
      sigBuilt = HashUtils.hashSHA256Base64(sig.toString());

      logger.info("sig after building: " + sigBuilt);
    } catch (Exception e) {
      logger.error("can not create sig:", e);
    }
    return sigBuilt;
  }

  public String buildSHA256() {
    logger.info("sig for build: " + sig);
    String sigBuilt ="";
    try {
      sigBuilt = HashUtils.hashSHA256(sig.toString());

      logger.info("sig after building: " + sigBuilt);
    } catch (Exception e) {
      logger.error("can not create sig:", e);
    }
    return sigBuilt;
  }
}
