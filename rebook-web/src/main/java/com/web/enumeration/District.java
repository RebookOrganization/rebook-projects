package com.web.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum  District {
    QUAN1(1, "Quận 1"),
    QUAN2(2, "Quận 2"),
    QUAN3(3, "Quận 3"),
    QUAN4(4, "Quận 4"),
    QUAN5(5, "Quận 5"),
    QUAN6(6, "Quận 6"),
    QUAN7(7, "Quận 7"),
    QUAN8(8, "Quận 8"),
    QUAN9(9, "Quận 9"),
    QUAN10(10, "Quận 10"),
    QUAN11(11, "Quận 11"),
    QUAN12(12, "Quận 12"),
    BINHTAN(13, "Bình Tân"),
    BINHTHANH(14, "Bình Thạnh"),
    GOVAP(15, "Gò Vấp"),
    PHUNHUAN(16, "Phú Nhuận"),
    TANBINH(17, "Tân Bình"),
    TANPHU(18, "Tân Phú"),
    THUDUC(19, "Thủ Đức"),
    BINHCHANH(20, "Bình Chánh"),
    CANGIO(21, "Cần Giờ"),
    CUCHI(22, "Củ Chi"),
    HOOCMON(23, "Hooc Môn"),
    NHABE(24, "Nhà Bè"),
    ;

    private final int value;
    private final String displayValue;

    District(int value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public int getValue() { return value; }

    public String getDisplayValue() { return displayValue; }

    public static District fromValue(int value) {
        for(District advert : District.values()) {
            if(advert.getValue() == value){
                return advert;
            }
        }
        return null;
    }

    public static District fromDisplayValue(String displayValue) {
        for (District district : District.values()){
            if (displayValue.contains(district.getDisplayValue())){
                return district;
            }
        }
        return null;
    }

    public static Map<Integer, String> toHashMap() {
        Map<Integer, String> adverts = new HashMap<>();
        for(District advert : District.values()){
            adverts.put(advert.getValue(), advert.getDisplayValue());
        }
        return adverts;
    }
}
