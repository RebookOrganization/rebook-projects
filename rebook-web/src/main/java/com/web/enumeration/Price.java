package com.web.enumeration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Price {
    THOATHUAN(0, "Thỏa Thuận", 0, 0),
    DUOI500(1, "< 500 Triệu", 0, 500),
    T500D800(2, "500 - 800 Triệu", 500, 800),
    T800D1TY(3, "800tr - 1 tỷ", 800, 1000),
    T1TYD2TY(4, "1 - 2 tỷ", 1000, 2000),
    T2TYD3TY(5, "2 - 3 tỷ", 2000, 3000),
    T3TYD5TY(6, "3 - 5 tỷ", 3000, 5000),
    T5TYD7TY(7, "5 - 7 tỷ", 5000, 7000),
    T7TY10TY(8, "7 - 10 tỷ", 7000, 10000),
    T10TYD20TY(9, "10 - 20 tỷ", 10000, 20000),
    T20TYDEN30TY(10, "20 - 30 tỷ", 20000, 30000),
    TREN30TY(11, "> 30 tỷ", 30000, 100000)
    ;

    private final int value;
    private final String displayValue;
    private final int from;
    private final int to;

    Price(int value, String displayValue, int from, int to) {
        this.value = value;
        this.displayValue = displayValue;
        this.from = from;
        this.to = to;
    }

    public int getValue() { return value; }

    public String getDisplayValue() { return displayValue; }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

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
