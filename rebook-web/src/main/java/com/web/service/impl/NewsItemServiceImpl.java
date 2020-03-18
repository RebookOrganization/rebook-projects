package com.web.service.impl;

import com.web.bean.Response.CommonResponse;
import com.web.bean.Response.CommonResponse.Fail;
import com.web.bean.Response.NewsResponseDTO;
import com.web.cache.CacheDataService;
import com.web.cache.NewsItemIndex;
import com.web.dto.RequestFilterSearchDto;
import com.web.model.*;
import com.web.repository.*;
import com.web.service.ApiService;
import com.web.service.NewsItemService;
import com.web.service.ObjectMapperService;
import com.web.utils.DateTimeUtils;
import com.web.utils.GenerateRandom;
import com.web.utils.GsonUtils;
import com.web.utils.StringUtils;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsItemServiceImpl implements NewsItemService {

  private static Logger logger = LoggerFactory.getLogger(NewsItemServiceImpl.class);

  @Autowired
  private NewsItemRepository newsRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserSearchLogRepository userSearchLogRepository;

  @Autowired
  CacheDataService cacheDataService;

  @Autowired
  private PropertyAdressRepository propertyAdressRepository;

  private int returnCode = 1;
  private String returnMessage = "success";
  private static Integer currentPartition = DateTimeUtils.getPartition();

  private ApiService apiService;
  private ObjectMapperService objectMapperService;

  @Autowired
  public NewsItemServiceImpl(ApiService apiService,
      ObjectMapperService objectMapperService) {
    this.apiService = apiService;
    this.objectMapperService = objectMapperService;
  }

  @Override
  public CommonResponse getAllNewsItem(int offset) {
    try {
      Map<String, NewsItem> newsMap = NewsItemIndex.newsItemMap;
      List<NewsResponseDTO> newsResponseDTOList = new ArrayList<>();

      List<NewsResponseDTO> esResponse = apiService.esNewsScrollApi(offset);
      if (esResponse != null && !esResponse.isEmpty()) {
        newsResponseDTOList.addAll(esResponse);
      }
      else {
        if (!newsMap.isEmpty() && newsMap.size() >= 20) {
          for (Map.Entry<String, NewsItem> entry : newsMap.entrySet()) {
            NewsItem newsItem = entry.getValue();
            if (newsItem != null) {
              newsResponseDTOList.add(objectMapperService.mapNewsToNewsResponseDTO(newsItem, currentPartition));
            }
          }
        }
        else {
          List<NewsItem> newsItemList = newsRepository.findNewsByPartition(currentPartition);
          for (NewsItem newsItem : newsItemList) {
            newsResponseDTOList.add(objectMapperService.mapNewsToNewsResponseDTO(newsItem, currentPartition));
          }
        }
      }

      logger.info("NewsItemServiceImpl getAllNewsItem response - {}, size - {}", newsResponseDTOList, newsResponseDTOList.size());

      return new CommonResponse<>(this.returnCode, this.returnMessage, newsResponseDTOList);
    } catch (Exception ex) {
      logger.error("NewsItemServiceImpl getAllNewsItem Exception: "+ ex);
      return new CommonResponse.Fail("NewsItemServiceImpl getAllNewsItem Exception.");
    }
  }

  @Override
  public CommonResponse getAllNewsByUser(Long userID) throws IOException {
    try {
      List<NewsItem> newsItemList;
      List<NewsResponseDTO> newsResponseDTOList = new ArrayList<>();
      Optional<User> user = userRepository.findById(userID);
      if (user.isPresent()) {
        newsItemList = newsRepository.findAllByUser(user.get());
        for (NewsItem newsItem : newsItemList) {
          newsResponseDTOList.add(objectMapperService.mapNewsToNewsResponseDTO(newsItem, currentPartition));
        }
      }
      return new CommonResponse<>(this.returnCode, this.returnMessage, newsResponseDTOList);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      logger.error("NewsItemServiceImpl getAllNewsByUser Exception: ", ex);
      return new Fail("Lấy thông tin bài viết của user thất bại.");
    }
  }

  @Override
  public CommonResponse esSearchNewsApi(RequestFilterSearchDto request) throws IOException {
    try {
      //save log of user search
      UserSearchLog userSearchLog = new UserSearchLog();
      userSearchLog.setAddress(request.getContent());
      userSearchLog.setArea(String.valueOf(request.getAreaTo()));
      userSearchLog.setContent(String.valueOf(request.getContent()));
      userSearchLog.setPrice(String.valueOf(request.getPriceTo()));
      userSearchLog.setCity(request.getProvinceCity());
      userSearchLogRepository.save(userSearchLog);

      List<NewsResponseDTO> newsResponseDTOList = apiService.esSearchNewsApi(request);
      return new CommonResponse<>(this.returnCode, this.returnMessage, newsResponseDTOList);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      logger.error("NewsItemServiceImpl getAllNewsByUser Exception: ", ex);
      return new Fail("Tìm kiếm thông tin bài viết của user thất bại.");
    }
  }

  @Override
  public CommonResponse getNewsById(String id) throws IOException {
    NewsResponseDTO newsResponseDTO = new NewsResponseDTO();
    try {
      int randomId = GenerateRandom.randomNewsId();
      newsResponseDTO = apiService.esNewsItemById(String.valueOf(randomId));

      if (newsResponseDTO == null) {
        Optional<NewsItem> newsItem = newsRepository.findById(Long.parseLong(String.valueOf(randomId)));
        if (newsItem.isPresent()) {
          newsResponseDTO = objectMapperService.mapNewsToNewsResponseDTO(newsItem.get(), currentPartition);
        }
      }

      return new CommonResponse<>(this.returnCode, this.returnMessage, newsResponseDTO);
    }
    catch (Exception ex) {
      logger.error("getNewsById exception: ", ex);
      return new CommonResponse.Fail("Lấy thông tin bài viết thất bại.");
    }
  }

  @Override
  public CommonResponse getNewsByIdAndPartition(String id, int partition) {
    try {
      Optional<NewsItem> newsItem = newsRepository.findByPartitionAndId(partition, Long.parseLong(id));
      NewsResponseDTO newsResponseDTO = new NewsResponseDTO();
      if (newsItem.isPresent()) {
        newsResponseDTO = objectMapperService.mapNewsToNewsResponseDTO(newsItem.get(), partition);
      }
      return new CommonResponse<>(this.returnCode, this.returnMessage, newsResponseDTO);
    }
    catch (Exception ex) {
      logger.error("getNewsByIdAndPartition exception: ", ex);
      return new CommonResponse.Fail("Lấy thông tin bài viết thất bại.");
    }
  }

  @Override
  public CommonResponse searchNewsInMySQL(RequestFilterSearchDto request) {
    try {
      List<NewsResponseDTO> newsResponseDTOList = queryNewsItem(request, currentPartition);

      int previousPartition = DateTimeUtils.getPreviousPartition();
      try {
        if (newsResponseDTOList.size() <= 10) {
          List<NewsResponseDTO> newsDTOPrePartition = queryNewsItem(request, previousPartition);
          newsResponseDTOList.addAll(newsDTOPrePartition);
        }
      }
      catch (Exception e) {
        logger.error("searchNewsInMySQL in previous partition exception: ", e);
      }

      logger.info("searchNewsInMySQL result: {}", newsResponseDTOList);
      return new CommonResponse<>(this.returnCode, this.returnMessage, newsResponseDTOList.size(), newsResponseDTOList);
    }
    catch (Exception ex) {
      logger.error("searchNewsInMySQL exception: ", ex);
      return new CommonResponse.Fail("Lấy thông tin bài viết thất bại.");
    }

  }

  private List<NewsResponseDTO> queryNewsItem(RequestFilterSearchDto request, int partition) {
    List<NewsResponseDTO> newsResponseDTOList = new ArrayList<>();
    int priceFrom = request.getPriceFrom();
    int priceTo = request.getPriceTo();
    int areaFrom = request.getAreaFrom();
    int areaTo = request.getAreaTo();
    String district = request.getDistrict();
    String province = request.getProvinceCity();
    String content = request.getContent();
    String directHouse = request.getDirectHouse();

    // Find with Address in partition
    List<Long> addressIDList = propertyAdressRepository.findAllIdBySummaryInPartition(partition, content, province, district);
    logger.info("queryNewsItem addressIDList: {}", addressIDList);

    if (!addressIDList.isEmpty()) {
      List<NewsItem> newsItemList = newsRepository.findAllByAddressIdAndPartition(partition, addressIDList);
      for (NewsItem newsItem : newsItemList) {
        float areaNum = newsItem.getAreaNum();
        double priceNum = newsItem.getPriceNum();
        String directOfHouse = newsItem.getDirect_of_house();

        if (priceNum >= priceFrom && priceNum <= priceTo) {
          NewsResponseDTO newsResponseDTO = objectMapperService.mapNewsToNewsResponseDTO(newsItem, partition);
          newsResponseDTOList.add(newsResponseDTO);
        }

        if (areaNum >= areaFrom && areaNum <= areaTo) {
          NewsResponseDTO newsResponseDTO = objectMapperService.mapNewsToNewsResponseDTO(newsItem, partition);
          newsResponseDTOList.add(newsResponseDTO);
        }

        if (directOfHouse != null && !directHouse.equals("") && StringUtils.like(directOfHouse, directHouse)) {
          NewsResponseDTO newsResponseDTO = objectMapperService.mapNewsToNewsResponseDTO(newsItem, partition);
          newsResponseDTOList.add(newsResponseDTO);
        }
      }
    }
    else {
      if (priceTo != 0) {
        List<NewsItem> listByPrice = newsRepository.findAllByPriceNum(partition, priceFrom, priceTo);
        for (NewsItem newsItem : listByPrice) {
          NewsResponseDTO newsResponseDTO = objectMapperService.mapNewsToNewsResponseDTO(newsItem, partition);
          newsResponseDTOList.add(newsResponseDTO);
        }
      }

      if (areaTo != 0) {
        List<NewsItem> listByArea = newsRepository.findAllByAreaNum(partition, areaFrom, areaTo);
        for (NewsItem newsItem : listByArea) {
          NewsResponseDTO newsResponseDTO = objectMapperService.mapNewsToNewsResponseDTO(newsItem, partition);
          newsResponseDTOList.add(newsResponseDTO);
        }
      }
    }

    return newsResponseDTOList;
  }

}
