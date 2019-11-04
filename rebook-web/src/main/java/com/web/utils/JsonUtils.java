package com.web.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
  private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

  private static final ObjectMapper objectMapper;

  static {
    JsonFactory factory = new JsonFactory();
    factory.setCharacterEscapes(new OwaspCharacterEscapes());
    objectMapper = new ObjectMapper(factory);
  }

  public static String toJsonString(Object object) {

    try {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      logger.error(e.getMessage(), e);
    }

    return "";
  }
}
