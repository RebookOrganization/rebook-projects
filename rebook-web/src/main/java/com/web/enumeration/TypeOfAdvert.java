package com.web.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum TypeOfAdvert {
    NORMALNEWS(1, "Tin thường"),
    PREFERENTIAL(2, "Tin ưu đãi"),
    VIPNEWS1(3, "Tin VIP 1"),
    VIPNEWS2(4, "Tin VIP 2"),
    VIPNEW3(5, "Tin VIP 3"),
    SPECIALVIP(6, "Vip đặc biệt");

    private final int value;
    private final String displayValue;

    TypeOfAdvert(int value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public int getValue() { return value; }

    public String getDisplayValue() { return displayValue; }

    public static TypeOfAdvert fromValue(int value) {
        for(TypeOfAdvert advert : TypeOfAdvert.values()) {
            if(advert.getValue() == value){
                return advert;
            }
        }
        return null;
    }

    public static Map<Integer, String> toHashMap() {
        Map<Integer, String> adverts = new HashMap<>();
        for(TypeOfAdvert advert : TypeOfAdvert.values()){
            adverts.put(advert.getValue(), advert.getDisplayValue());
        }
        return adverts;
    }
}

