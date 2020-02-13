package com.web.dto;

import java.util.Date;

public class CommentNewsDTO {
  private Long id;
  private Long userId;
  private Long newItemId;
  private String content;
  private Date commentTime;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getCommentTime() {
    return commentTime;
  }

  public void setCommentTime(Date commentTime) {
    this.commentTime = commentTime;
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
