package com.web.dto;

public class RequestFilterSearchDto {

  private String content;
  private String priceFrom;
  private String priceTo;
  private String areaFrom;
  private String areaTo;
  private String district;
  private String provinceCity;
  private String transType;
  private String directHouse;

  public String getContent() { return content; }

  public void setContent(String content) { this.content = content; }

  public String getPriceFrom() { return priceFrom; }

  public void setPriceFrom(String priceFrom) { this.priceFrom = priceFrom; }

  public String getPriceTo() { return priceTo; }

  public void setPriceTo(String priceTo) { this.priceTo = priceTo; }

  public String getAreaFrom() { return areaFrom; }

  public void setAreaFrom(String areaFrom) { this.areaFrom = areaFrom; }

  public String getAreaTo() { return areaTo; }

  public void setAreaTo(String areaTo) { this.areaTo = areaTo; }

  public String getDistrict() { return district; }

  public void setDistrict(String district) { this.district = district; }

  public String getProvinceCity() { return provinceCity; }

  public void setProvinceCity(String provinceCity) { this.provinceCity = provinceCity; }

  public String getTransType() { return transType; }

  public void setTransType(String transType) { this.transType = transType; }

  public String getDirectHouse() { return directHouse; }

  public void setDirectHouse(String directHouse) { this.directHouse = directHouse; }

}
