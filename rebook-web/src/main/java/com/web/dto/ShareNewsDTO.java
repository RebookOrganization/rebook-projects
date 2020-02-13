package com.web.dto;

import java.util.Date;

public class ShareNewsDTO {
  private Long id;
  private Long userId;
  private Long newItemId;
  private boolean isShare;
  private Date shareTime;
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

  public Long getNewItemId() {
    return newItemId;
  }

  public void setNewItemId(Long newItemId) {
    this.newItemId = newItemId;
  }

  public boolean isShare() {
    return isShare;
  }

  public void setShare(boolean share) {
    isShare = share;
  }

  public Date getShareTime() {
    return shareTime;
  }

  public void setShareTime(Date shareTime) {
    this.shareTime = shareTime;
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
