package com.web.dto;

public class RequestFilterSearchDto {

  private String content;
  private int priceFrom;
  private int priceTo;
  private int areaFrom;
  private int areaTo;
  private String district;
  private String provinceCity;
  private String transType;
  private String directHouse;

  public String getContent() { return content; }

  public void setContent(String content) { this.content = content; }

  public int getPriceFrom() {
    return priceFrom;
  }

  public void setPriceFrom(int priceFrom) {
    this.priceFrom = priceFrom;
  }

  public int getPriceTo() {
    return priceTo;
  }

  public void setPriceTo(int priceTo) {
    this.priceTo = priceTo;
  }

  public int getAreaFrom() {
    return areaFrom;
  }

  public void setAreaFrom(int areaFrom) {
    this.areaFrom = areaFrom;
  }

  public int getAreaTo() {
    return areaTo;
  }

  public void setAreaTo(int areaTo) {
    this.areaTo = areaTo;
  }

  public String getDistrict() { return district; }

  public void setDistrict(String district) { this.district = district; }

  public String getProvinceCity() { return provinceCity; }

  public void setProvinceCity(String provinceCity) { this.provinceCity = provinceCity; }

  public String getTransType() { return transType; }

  public void setTransType(String transType) { this.transType = transType; }

  public String getDirectHouse() { return directHouse; }

  public void setDirectHouse(String directHouse) { this.directHouse = directHouse; }

}
