package com.crawler.cache;

import com.crawler.model.NewsItem;
import com.crawler.repository.ImagesRepository;
import com.crawler.repository.NewsItemRepository;
import com.crawler.repository.UserRepository;
import com.crawler.utils.DateTimeUtils;
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

  private Logger logger = LoggerFactory.getLogger(NewsItemIndex.class);

  public static NewsItem newsItem = new NewsItem();

  private static Integer partition = DateTimeUtils.getPartition();

  @Autowired
  CacheDataService cacheDataService;

  @PostConstruct
  public void mapToIndexNewsItem() {
    indexNewsItem(partition);
  }

  public void indexNewsItem(Integer partition) {
    NewsItemIndex.newsItem = cacheDataService.findLastRow(partition);
  }

}
