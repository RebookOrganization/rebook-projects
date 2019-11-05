package com.web.dto;

public class RequestFilterSearchDto {

  private String content;
  private String price;
  private String area;
  private String district;
  private String provinceCity;
  private String transType;
  private String directHouse;

  public String getContent() { return content; }

  public void setContent(String content) { this.content = content; }

  public String getPrice() { return price; }

  public void setPrice(String price) { this.price = price; }

  public String getArea() { return area; }

  public void setArea(String area) { this.area = area; }

  public String getDistrict() { return district; }

  public void setDistrict(String district) { this.district = district; }

  public String getProvinceCity() { return provinceCity; }

  public void setProvinceCity(String provinceCity) { this.provinceCity = provinceCity; }

  public String getTransType() { return transType; }

  public void setTransType(String transType) { this.transType = transType; }

  public String getDirectHouse() { return directHouse; }

  public void setDirectHouse(String directHouse) { this.directHouse = directHouse; }
}
