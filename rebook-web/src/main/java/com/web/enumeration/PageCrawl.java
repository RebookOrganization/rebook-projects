package com.web.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum PageCrawl {
  BATDONGSAN(1, "BatDongSan.com.vn"),
  DIAOCONLINE(2, "DiaOcOnline.vn")
  ;

  private final int value;
  private final String displayValue;

  PageCrawl(int value, String displayValue) {
    this.value = value;
    this.displayValue = displayValue;
  }

  public int getValue() { return value; }

  public String getDisplayValue() { return displayValue; }

  public static PageCrawl fromValue(int value) {
    for(PageCrawl advert : PageCrawl.values()) {
      if(advert.getValue() == value){
        return advert;
      }
    }
    return null;
  }

  public static Map<Integer, String> toHashMap() {
    Map<Integer, String> adverts = new HashMap<>();
    for(PageCrawl advert : PageCrawl.values()){
      adverts.put(advert.getValue(), advert.getDisplayValue());
    }
    return adverts;
  }
}
