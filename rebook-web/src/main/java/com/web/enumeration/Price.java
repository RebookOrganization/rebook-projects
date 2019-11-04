package com.web.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum Price {
    THOATHUAN(0, "Thỏa Thuận"),
    DUOI500(1, "< 500 Triệu"),
    T500D800(2, "500 - 800 Triệu"),
    T800D1TY(3, "800tr - 1 tỷ"),
    T1TYD2TY(4, "1 - 2 tỷ"),
    T2TYD3TY(5, "2 - 3 tỷ"),
    T3TYD5TY(6, "3 - 5 tỷ"),
    T5TYD7TY(7, "5 - 7 tỷ"),
    T7TY10TY(8, "7 - 10 tỷ"),
    T10TYD20TY(9, "10 - 20 tỷ"),
    T20TYDEN30TY(10, "20 - 30 tỷ"),
    TREN30TY(11, "> 30 tỷ")
    ;

    private final int value;
    private final String displayValue;

    Price(int value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public int getValue() { return value; }

    public String getDisplayValue() { return displayValue; }

    public static Price fromValue(int value) {
        for(Price advert : Price.values()) {
            if(advert.getValue() == value){
                return advert;
            }
        }
        return null;
    }

    public static Map<Integer, String> toHashMap() {
        Map<Integer, String> adverts = new HashMap<>();
        for(Price advert : Price.values()){
            adverts.put(advert.getValue(), advert.getDisplayValue());
        }
        return adverts;
    }
}
