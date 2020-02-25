package com.web.bean.Response;

public class KnnRecommendResponse {
  private String data;
  private String description;
  private int total;

  public KnnRecommendResponse(String data, String description, int total) {
    this.data = data;
    this.description = description;
    this.total = total;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
}
