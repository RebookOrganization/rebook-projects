package com.web.utils;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class CallApiUtils {

  private static final Logger logger = LoggerFactory.getLogger(CallApiUtils.class);
  private static final int TIME_OUT = 60000;

  public HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
        = new HttpComponentsClientHttpRequestFactory();
    try {
      //Connect timeout
      clientHttpRequestFactory.setConnectTimeout(TIME_OUT);

      //Read timeout
      clientHttpRequestFactory.setReadTimeout(TIME_OUT);
      return clientHttpRequestFactory;
    } catch (NullPointerException | NumberFormatException ex) {
      clientHttpRequestFactory.setConnectTimeout(TIME_OUT);

      clientHttpRequestFactory.setReadTimeout(TIME_OUT);
      return clientHttpRequestFactory;
    }
  }

  @NonNull
  public String sendGet(@NotNull UriComponentsBuilder builder) throws IOException {
    RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

    org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

    org.springframework.http.HttpEntity<?> entity = new org.springframework.http.HttpEntity<>(
        headers);

    ResponseEntity<String> response = restTemplate
        .exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

    if (response.getStatusCode().value() != 200) {
      throw new IOException("Failed : HTTP getStatusCode: " + response.getStatusCode().value());
    }
    return response.getBody();
  }

  public String sendPost(List<BasicNameValuePair> params, String url) throws IOException {
    RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
    MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<>();

    for (BasicNameValuePair param : params) {
      postParameters.add(param.getName(), param.getValue());
    }

    ResponseEntity<String> response = restTemplate.postForEntity(url, postParameters, String.class);
    if (response.getStatusCode().value() != 200) {
      throw new IOException("Failed : HTTP getStatusCode: " + response.getStatusCode().value());
    }
    return response.getBody();
  }

  public String sendPostJson(JsonObject object, String url) throws IOException {
    RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<String> request = new HttpEntity<>(GsonUtils.toJsonString(object), headers);

    ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
    if (response.getStatusCode().value() != 200) {
      throw new IOException("Failed : HTTP getStatusCode: " + response.getStatusCode().value());
    }

    return response.getBody();
  }

}
