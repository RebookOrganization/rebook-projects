package com.web.service.impl;

import com.web.bean.Response.KnnRecommendResponse;
import com.web.bean.Response.NewsResponseDTO;
import com.web.config.WebAppConfig;
import com.web.model.Comment;
import com.web.model.LikeNews;
import com.web.model.NewsItem;
import com.web.model.ShareNews;
import com.web.repository.CommentRepository;
import com.web.repository.LikeRepository;
import com.web.repository.NewsItemRepository;
import com.web.repository.ShareRepository;
import com.web.service.ObjectMapperService;
import com.web.service.RecommendService;
import com.web.utils.CallApiUtils;
import com.web.utils.DateTimeUtils;
import com.web.utils.GenerateRandom;
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
  private LikeRepository likeRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private ShareRepository shareRepository;

  @Autowired
  public RecommendServiceImpl(ObjectMapperService objectMapperService) {
    this.objectMapperService = objectMapperService;
  }

  @Override
  public List<NewsResponseDTO> listRecommend(String userId) {
    final String knnResponse;
    final List<NewsResponseDTO> responseDTOList = new ArrayList<>();
    final String url = WebAppConfig.knnRecommendUrl;
    String newsId;
    try {
      String currentPartition = String.valueOf(DateTimeUtils.getPartition());
      String previousPartition = String.valueOf(DateTimeUtils.getPreviousPartition());

      LikeNews likeNews = likeRepository.findLastLikeByUserId(Long.parseLong(userId));
      if (likeNews != null) {
        newsId = String.valueOf(likeNews.getNewsItemId());
      }
      else {
        Comment comment = commentRepository.findLastCommentByUserId(Long.parseLong(userId));
        if (comment != null) {
          newsId = String.valueOf(comment.getNewItemId());
        }
        else {
          ShareNews shareNews = shareRepository.findLastShareByUserId(Long.parseLong(userId));
          if (shareNews != null) {
            newsId = String.valueOf(shareNews.getNewItemId());
          }
          else {
            newsId = String.valueOf(GenerateRandom.randomNewsId());
          }
        }
      }

      final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
          .queryParam("prefix", "item" + currentPartition)
          .queryParam("id", newsId)
          .queryParam("include", previousPartition + "," + currentPartition);

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
          long id = Long.parseLong(news[1]);
          Optional<NewsItem> newsItem = newsItemRepository.findByPartitionAndId(partition, id);
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
