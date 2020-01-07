package com.web.bean.Response;

public class EsNewsItemById extends ElasticBaseResponse {

  private NewsResponseDTO result;

  public EsNewsItemById(int returnCode, String returnMessage, int totalHits, NewsResponseDTO result) {
    super(returnCode, returnMessage, totalHits);
    this.result = result;
  }

  public NewsResponseDTO getResult() {
    return result;
  }

  public void setResult(NewsResponseDTO result) {
    this.result = result;
  }
}
