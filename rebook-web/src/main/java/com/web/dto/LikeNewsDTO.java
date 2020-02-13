package com.web.dto;

import java.util.Date;

public class LikeNewsDTO {
  private Long id;
  private Long userId;
  private Long newsItemId;
  private boolean isLike;
  private Date likeTime;
  private String userName;
  private String userImageUrl;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getNewsItemId() {
    return newsItemId;
  }

  public void setNewsItemId(Long newsItemId) {
    this.newsItemId = newsItemId;
  }

  public boolean isLike() {
    return isLike;
  }

  public void setLike(boolean like) {
    isLike = like;
  }

  public Date getLikeTime() {
    return likeTime;
  }

  public void setLikeTime(Date likeTime) {
    this.likeTime = likeTime;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserImageUrl() {
    return userImageUrl;
  }

  public void setUserImageUrl(String userImageUrl) {
    this.userImageUrl = userImageUrl;
  }
}
