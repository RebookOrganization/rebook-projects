package com.web.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum TransType {
    FORSALE(1, "Nhà đất bán"),
    FORRENT(2, "Nhà đất thuê");

    private final int value;
    private final String displayValue;

    TransType(int value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public int getValue() { return value; }

    public String getDisplayValue() { return displayValue; }

    public static TransType fromValue(int value) {
        for(TransType advert : TransType.values()) {
            if(advert.getValue() == value){
                return advert;
            }
        }
        return null;
    }

    public static Map<Integer, String> toHashMap() {
        Map<Integer, String> adverts = new HashMap<>();
        for(TransType advert : TransType.values()){
            adverts.put(advert.getValue(), advert.getDisplayValue());
        }
        return adverts;
    }
}
