package com.web.bean.Response;

public class CommonResponse<T> {

  protected int returnCode;
  protected String returnMessage;
  private int totalHits;
  private T result;

  public CommonResponse(int returnCode, String returnMessage, T result) {
    this.returnCode = returnCode;
    this.returnMessage = returnMessage;
    this.result = result;
  }

  public CommonResponse() {
  }

  public CommonResponse(int returnCode, String returnMessage, int totalHits, T result) {
    this.returnCode = returnCode;
    this.returnMessage = returnMessage;
    this.totalHits = totalHits;
    this.result = result;
  }

  public static class Fail extends CommonResponse {

    public Fail (String returnMessage) {
      super();
      this.returnCode = 0;
      this.returnMessage = returnMessage;
    }
  }

  public int getReturnCode() { return returnCode; }

  public void setReturnCode(int returnCode) { this.returnCode = returnCode; }

  public String getReturnMessage() { return returnMessage; }

  public void setReturnMessage(String returnMessage) { this.returnMessage = returnMessage; }

  public T getResult() { return result; }

  public void setResult(T result) { this.result = result; }

  public int getTotalHits() {
    return totalHits;
  }

  public void setTotalHits(int totalHits) {
    this.totalHits = totalHits;
  }
}
