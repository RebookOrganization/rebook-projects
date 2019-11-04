package com.web.cache;

import com.web.model.ChatMessage;
import com.web.model.NewsItem;
import com.web.repository.ChatMessageRepository;
import com.web.repository.NewsItemRepository;
import com.web.utils.DateTimeUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

@Component
public class CacheDataService {

  @Autowired
  NewsItemRepository newsItemRepository;

  @Autowired
  ChatMessageRepository chatMessageRepository;

  @CacheEvict(value = "findAllNewsItem", allEntries = true)
  public List<NewsItem> findAllNewsItem() {
    return newsItemRepository.findAll();
  }

  @CacheEvict(value = "findNewsThreeDateBefore", allEntries = true)
  public List<NewsItem> findNewsThreeDateBefore() {
    long dateFrom = DateTimeUtils.getThreeDateBefore();
    long dateTo = DateTimeUtils.getCurrentDateMilisec();
    return newsItemRepository.findAllByPostedDate(dateFrom, dateTo);
  }

  @CacheEvict(value = "findNewsByPartition", allEntries = true)
  public List<NewsItem> findNewsByPartition(int partition) {
    return newsItemRepository.findNewsByPartition(partition);
  }

  @CacheEvict(value = "findLastRow", allEntries = true)
  public NewsItem findLastRow(int partition) {
    return newsItemRepository.findLastRow(partition);
  }

  @CachePut(value = "listAllMessages")
  public List<ChatMessage> findAllMessages() {
    return chatMessageRepository.findAll();
  }

}
