package com.web.service.impl;

import com.web.bean.Response.KnnRecommendResponse;
import com.web.bean.Response.NewsResponseDTO;
import com.web.config.WebAppConfig;
import com.web.model.NewsItem;
import com.web.repository.NewsItemRepository;
import com.web.service.ObjectMapperService;
import com.web.service.RecommendService;
import com.web.utils.CallApiUtils;
import com.web.utils.GsonUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RecommendServiceImpl implements RecommendService {

  private static final Logger logger = LoggerFactory.getLogger(RecommendServiceImpl.class);
  private CallApiUtils callApiUtils = new CallApiUtils();
  private ObjectMapperService objectMapperService;

  @Autowired
  private NewsItemRepository newsItemRepository;

  @Autowired
  public RecommendServiceImpl(ObjectMapperService objectMapperService) {
    this.objectMapperService = objectMapperService;
  }

  @Override
  public List<NewsResponseDTO> listRecommend(String prefix, String id, String include) {
    final String knnResponse;
    final List<NewsResponseDTO> responseDTOList = new ArrayList<>();
    final String url = WebAppConfig.knnRecommendUrl;
    try {
      final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
          .queryParam("prefix", prefix)
          .queryParam("id", id)
          .queryParam("include", include);

      logger.info("listRecommend url: {}, builder: {}", url, builder.toUriString());

      knnResponse = callApiUtils.sendGet(builder);
      logger.info("listRecommend knnResponse: {}", knnResponse);

      KnnRecommendResponse response = GsonUtils.fromJsonString(knnResponse, KnnRecommendResponse.class);
      String data = response.getData();
      if (!data.equals("")) {
        String[] dataArray = data.split("\\s*,\\s*");

        for (String item : dataArray) {
          String[] news = item.split("_");
          int partition = Integer.parseInt(news[0]);
          long newsId = Long.parseLong(news[1]);
          Optional<NewsItem> newsItem = newsItemRepository.findByIdAndPartition(partition, newsId);
          if (newsItem.isPresent()) {
            NewsResponseDTO responseDTO = objectMapperService.mapNewsToNewsResponseDTO(newsItem.get());
            responseDTOList.add(responseDTO);
          }
        }
      }
    }
    catch (Exception ex) {
      logger.info("listRecommend exception - {}", ex.getMessage());
      ex.printStackTrace();
    }
    return responseDTOList;
  }
}
