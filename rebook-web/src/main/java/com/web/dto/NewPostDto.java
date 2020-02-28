package com.web.dto;

public class NewPostDto {
  private long newsId;
  private int partition;

  public NewPostDto(long newsId, int partition) {
    this.newsId = newsId;
    this.partition = partition;
  }

  public long getNewsId() {
    return newsId;
  }

  public void setNewsId(long newsId) {
    this.newsId = newsId;
  }

  public int getPartition() {
    return partition;
  }

  public void setPartition(int partition) {
    this.partition = partition;
  }
}
