package com.web.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.web.bean.Response.CommonResponse;
import com.web.bean.Response.EsNewsItemById;
import com.web.bean.Response.EsNewsItemResponse;
import com.web.bean.Response.NewsResponseDTO;
import com.web.config.WebAppConfig;
import com.web.dto.RequestFilterSearchDto;
import com.web.utils.CallApiUtils;
import com.web.utils.GsonUtils;
import com.web.utils.HashUtils;
import java.util.ArrayList;
import java.util.List;
import net.minidev.json.JSONObject;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ApiService {

  private static final Logger logger = LoggerFactory.getLogger(ApiService.class);
  private CallApiUtils callApiUtils = new CallApiUtils();

  public List<NewsResponseDTO> esNewsScrollApi(int offset) throws Exception {
    final String esResponse;
    List<NewsResponseDTO> newsResponseDTOList = new ArrayList<>();
    String url = WebAppConfig.esNewsScrollApiUrl;
    String sigInput = "";
    String mac = HashUtils.hashSHA256(sigInput);

    try {
      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
          .queryParam("offset", offset);

      logger.info("ApiService esNewsScrollApi url: {}, builder: {}", url, builder);

      esResponse = callApiUtils.sendGet(builder);

      logger.info("ApiService esNewsScrollApi response: {}", esResponse);
      EsNewsItemResponse response = GsonUtils.fromJsonString(esResponse, EsNewsItemResponse.class);
      newsResponseDTOList = response.getResult();
    }
    catch (Exception ex) {
      logger.info("ApiService esNewsScrollApi exception - {}", ex.getMessage());
      ex.printStackTrace();
    }

    return newsResponseDTOList;
  }

  public NewsResponseDTO esNewsItemById(String id) throws Exception {
    final String esResponse;
    String url = WebAppConfig.esNewsItemById;
    NewsResponseDTO newsResponseDTO = new NewsResponseDTO();
    try {
      UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
          .queryParam("id", id);

      logger.info("ApiService esNewsItemById url: {}, builder: {}", url, builder);
      esResponse = callApiUtils.sendGet(builder);

      logger.info("ApiService esNewsItemById response: {}", esResponse);
      EsNewsItemById response = GsonUtils.fromJsonString(esResponse, EsNewsItemById.class);
      newsResponseDTO = response.getResult();
    }
    catch (Exception ex) {
      logger.error("es getNewsById exception: ", ex);
    }

    return newsResponseDTO;
  }

  public List<NewsResponseDTO> esSearchNewsApi(RequestFilterSearchDto request) throws Exception {
    final String esResponse;
    List<NewsResponseDTO> newsResponseDTOList = new ArrayList<>();
    String url = WebAppConfig.esNewsItemSearchUrl;

    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("content", request.getContent());
    jsonObject.addProperty("priceFrom", request.getPriceFrom());
    jsonObject.addProperty("priceTo", request.getPriceTo());
    jsonObject.addProperty("areaFrom", request.getAreaFrom());
    jsonObject.addProperty("areaTo", request.getAreaTo());
    jsonObject.addProperty("district", request.getDistrict());
    jsonObject.addProperty("provinceCity", request.getProvinceCity());
    jsonObject.addProperty("transType", request.getTransType());
    jsonObject.addProperty("directHouse", request.getDirectHouse());

    logger.info("ApiService esSearchNewsApi request: {}", GsonUtils.toJsonString(request));
    try {
      esResponse = callApiUtils.sendPostJson(jsonObject, url);
      logger.info("ApiService esSearchNewsApi response: {}", esResponse);
      EsNewsItemResponse response = GsonUtils.fromJsonString(esResponse, EsNewsItemResponse.class);
      newsResponseDTOList = response.getResult();
    }
    catch (Exception ex) {
      logger.info("ApiService esNewsSearchApi exception - {}", ex.getMessage());
      ex.printStackTrace();
    }

    return newsResponseDTOList;
  }

  public double stringSimilar(String s1, String s2) {
    SimilarityStrategy strategy = new JaroWinklerStrategy();
    StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
    return service.score(s1, s2);
  }



}
