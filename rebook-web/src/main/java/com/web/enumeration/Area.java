package com.web.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum Area {
    NA(0,"Chưa xác định"),
    DUOI30M2(1, "< 30 m2"),
    T30D50(2, "30 - 50 m2"),
    T50D80(3, "50 - 80 m2"),
    T80D100(4, "80 - 100 m2"),
    T100D150(5, "100 - 150 m2"),
    T150D200(6, "150 - 200 m2"),
    T200D250(7, "200 - 250 m2"),
    T250D300(8, "250 - 300 m2"),
    T300D500(9, "300 - 500 m2"),
    TREN500(10, "> 500 m2"),
    ;

    private final int value;
    private final String displayValue;

    Area(int value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public int getValue() { return value; }

    public String getDisplayValue() { return displayValue; }

    public static Area fromValue(int value) {
        for(Area advert : Area.values()) {
            if(advert.getValue() == value){
                return advert;
            }
        }
        return null;
    }

    public static Map<Integer, String> toHashMap() {
        Map<Integer, String> adverts = new HashMap<>();
        for(Area advert : Area.values()){
            adverts.put(advert.getValue(), advert.getDisplayValue());
        }
        return adverts;
    }
}
