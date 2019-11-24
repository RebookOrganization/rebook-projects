package com.web.bean.Response;

public class ElasticBaseResponse {
  private int returnCode;
  private String returnMessage;
  private int totalHits;

  public ElasticBaseResponse(int returnCode, String returnMessage, int totalHits) {
    this.returnCode = returnCode;
    this.returnMessage = returnMessage;
    this.totalHits = totalHits;
  }

  public int getReturnCode() { return returnCode; }

  public void setReturnCode(int returnCode) { this.returnCode = returnCode; }

  public String getReturnMessage() { return returnMessage; }

  public void setReturnMessage(String returnMessage) { this.returnMessage = returnMessage; }

  public int getTotalHits() { return totalHits; }

  public void setTotalHits(int totalHits) { this.totalHits = totalHits; }

}
