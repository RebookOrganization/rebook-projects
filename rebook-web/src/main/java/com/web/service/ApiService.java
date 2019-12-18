package com.web.service;

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

  public List<NewsResponseDTO> esSearchNewsApi(RequestFilterSearchDto request) throws Exception {
    final String esResponse;
    List<NewsResponseDTO> newsResponseDTOList = new ArrayList<>();
    String url = WebAppConfig.esNewsItemSearchUrl;

    JSONObject object = new JSONObject();
    object.put("content", request.getContent());
    object.put("priceFrom", request.getPriceFrom());
    object.put("priceTo", request.getPriceTo());
    object.put("areaFrom", request.getAreaFrom());
    object.put("areaTo", request.getAreaTo());
    object.put("district", request.getDistrict());
    object.put("provinceCity", request.getProvinceCity());
    object.put("transType", request.getTransType());
    object.put("directHouse", request.getDirectHouse());

    logger.info("ApiService esSearchNewsApi request: {}", GsonUtils.toJsonString(object));
    try {
      esResponse = callApiUtils.sendPostJson(object, url);
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

}
