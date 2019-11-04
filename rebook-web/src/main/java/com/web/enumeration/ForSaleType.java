package com.web.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum ForSaleType {
    CHUNGCU(0, "Bán căn hộ chung cư"),
    NHARIENG(1, "Bán nhà riêng"),
    BIETTHU(2, "Bán nhà biệt thự, liền kề"),
    MATPHO(3, "Bán nhà mặt phố"),
    DUAN(4, "Bán đất nền dự án"),
    DAT(5, "Bán đất"),
    TRANGTRAI(6, "Bán trang trại, khu nghỉ dưỡng"),
    KHOXUONG(7, "Bán kho, nhà xưởng"),
    KHAC(8, "Bán loại bất động sản khác")
    ;

    private final int value;
    private final String displayValue;

    ForSaleType(int value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public int getValue() { return value; }

    public String getDisplayValue() { return displayValue; }

    public static ForSaleType fromValue(int value) {
        for(ForSaleType advert : ForSaleType.values()) {
            if(advert.getValue() == value){
                return advert;
            }
        }
        return null;
    }

    public static Map<Integer, String> toHashMap() {
        Map<Integer, String> adverts = new HashMap<>();
        for(ForSaleType advert : ForSaleType.values()){
            adverts.put(advert.getValue(), advert.getDisplayValue());
        }
        return adverts;
    }
}
