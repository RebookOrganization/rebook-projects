package com.web.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum ForRentType {
    CHUNGCU(0, "Cho thuê căn hộ chung cư"),
    NHARIENG(1, "Cho thuê nhà riêng"),
    MATPHO(2, "Cho thuê nhà mặt phố"),
    NHATRO(3, "Cho thuê nhà trọ, phòng trọ"),
    VANPHONG(4, "Cho thuê văn phòng"),
    CUAHANG(5, "Cho thuê cửa hàng - ki-ốt"),
    KHOXUONG(6, "Cho thuê kho, nhà xưởng, đất"),
    KHAC(7, "Cho thuê bất động sản khác"),
    ;

    private final int value;
    private final String displayValue;

    ForRentType(int value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public int getValue() { return value; }

    public String getDisplayValue() { return displayValue; }

    public static ForRentType fromValue(int value) {
        for(ForRentType advert : ForRentType.values()) {
            if(advert.getValue() == value){
                return advert;
            }
        }
        return null;
    }

    public static Map<Integer, String> toHashMap() {
        Map<Integer, String> adverts = new HashMap<>();
        for(ForRentType advert : ForRentType.values()){
            adverts.put(advert.getValue(), advert.getDisplayValue());
        }
        return adverts;
    }
}
