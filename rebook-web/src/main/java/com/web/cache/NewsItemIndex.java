package com.web.cache;

import com.web.model.NewsItem;
import com.web.repository.ImagesRepository;
import com.web.repository.NewsItemRepository;
import com.web.repository.UserRepository;
import com.web.service.impl.NewsItemServiceImpl;
import com.web.utils.DateTimeUtils;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Transactional
public class NewsItemIndex {

  private Logger logger = LoggerFactory.getLogger(NewsItemServiceImpl.class);

  public static Map<String, NewsItem> newsItemMap = new HashMap<>();

  public static NewsItem newsItem = new NewsItem();

  private static Integer partition = DateTimeUtils.getPartition();

  @Autowired
  CacheDataService cacheDataService;

  @PostConstruct
  public void mapToIndexNewsItem() {
    indexNewsItem(partition);
    mapToIndexNews(partition);
  }

  public void mapToIndexNews(Integer partition) {
    List<NewsItem> newsItemList = cacheDataService.findNewsByPartition(partition);
    if (newsItemList != null && !newsItemList.isEmpty()) {
      logger.info("NewsItemIndex newsItemMap - {}", NewsItemIndex.newsItemMap);
      NewsItemIndex.newsItemMap.clear();
      for (NewsItem item : newsItemList) {
        NewsItemIndex.newsItemMap.put(item.getUrl(), item);
      }
    }
  }

  public void indexNewsItem(Integer partition) {
    NewsItemIndex.newsItem = cacheDataService.findLastRow(partition);
  }

}
