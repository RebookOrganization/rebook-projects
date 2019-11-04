package com.crawler.bean.Request;

public class NewsFilterSearch {

    private String tranType;
    private String provinceCity;
    private String district;
    private String area;
    private String price;

    public String getTranType() { return tranType; }

    public void setTranType(String tranType) { this.tranType = tranType; }

    public String getProvinceCity() { return provinceCity; }

    public void setProvinceCity(String provinceCity) { this.provinceCity = provinceCity; }

    public String getDistrict() { return district; }

    public void setDistrict(String district) { this.district = district; }

    public String getArea() { return area; }

    public void setArea(String area) { this.area = area; }

    public String getPrice() { return price; }

    public void setPrice(String price) { this.price = price; }
}
