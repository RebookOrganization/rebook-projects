package com.web.enumeration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Area {
    NA(0,"Chưa xác định", 0, 0),
    DUOI30M2(1, "< 30 m2", 0, 30),
    T30D50(2, "30 - 50 m2", 30, 50),
    T50D80(3, "50 - 80 m2", 50, 80),
    T80D100(4, "80 - 100 m2", 80, 100),
    T100D150(5, "100 - 150 m2", 100, 150),
    T150D200(6, "150 - 200 m2", 150, 200),
    T200D250(7, "200 - 250 m2", 200, 250),
    T250D300(8, "250 - 300 m2", 250, 300),
    T300D500(9, "300 - 500 m2", 300, 500),
    TREN500(10, "> 500 m2", 500, 1000),
    ;

    private final int value;
    private final String displayValue;
    private final int from;
    private final int to;

    Area(int value, String displayValue, int from, int to) {
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
