package com.web.bean.Request;

public class CommentRequest {

  private String comment;
  private Long userId;
  private Long newsItemId;

  public String getComment() { return comment; }

  public void setComment(String comment) { this.comment = comment; }

  public Long getUserId() { return userId; }

  public void setUserId(Long userId) { this.userId = userId; }

  public Long getNewsItemId() { return newsItemId; }

  public void setNewsItemId(Long newsItemId) { this.newsItemId = newsItemId; }
}
