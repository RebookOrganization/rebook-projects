package com.crawler.bean.Request;

public class LikeRequest {

  private boolean isLike;
  private Long userId;
  private Long newsItemId;

  public boolean isLike() { return isLike; }

  public void setLike(boolean like) { isLike = like; }

  public Long getUserId() { return userId; }

  public void setUserId(Long userId) { this.userId = userId; }

  public Long getNewsItemId() { return newsItemId; }

  public void setNewsItemId(Long newsItemId) { this.newsItemId = newsItemId; }
}
