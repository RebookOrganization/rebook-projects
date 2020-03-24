package com.web.bean.Request;

public class ShareRequest {

  private Long userId;
  private Long newsItemId;
  private int share;

  public Long getUserId() { return userId; }

  public void setUserId(Long userId) { this.userId = userId; }

  public Long getNewsItemId() { return newsItemId; }

  public void setNewsItemId(Long newsItemId) { this.newsItemId = newsItemId; }

  public int getShare() { return share; }

  public void setShare(int share) { this.share = share; }

  @Override
  public String toString() {
    return "ShareRequest{" +
        "userId=" + userId +
        ", newsItemId=" + newsItemId +
        ", isShare=" + share +
        '}';
  }
}
