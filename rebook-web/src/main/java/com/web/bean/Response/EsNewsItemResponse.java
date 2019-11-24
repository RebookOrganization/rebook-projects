package com.web.bean.Response;

import java.util.List;

public class EsNewsItemResponse extends ElasticBaseResponse {

  private List<NewsResponseDTO> result;

  public EsNewsItemResponse(int returnCode, String returnMessage, int totalHits,
      List<NewsResponseDTO> result) {
    super(returnCode, returnMessage, totalHits);
    this.result = result;
  }

  public List<NewsResponseDTO> getResult() {
    return result;
  }

  public void setResult(List<NewsResponseDTO> result) {
    this.result = result;
  }
}
