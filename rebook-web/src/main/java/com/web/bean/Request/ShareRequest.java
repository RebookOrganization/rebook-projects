package com.web.bean.Request;

public class ShareRequest {

  private Long userId;
  private Long newsItemId;
  private boolean isShare;

  public Long getUserId() { return userId; }

  public void setUserId(Long userId) { this.userId = userId; }

  public Long getNewsItemId() { return newsItemId; }

  public void setNewsItemId(Long newsItemId) { this.newsItemId = newsItemId; }

  public boolean isShare() { return isShare; }

  public void setShare(boolean share) { isShare = share; }
}
