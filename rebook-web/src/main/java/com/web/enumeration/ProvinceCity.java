package com.web.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum  ProvinceCity {
    HCM(1, "Hồ Chí Minh"),
    HANOI(2, "Hà Nội"),
    DANANG(3, "Đà Nẵng"),
    HAIPHONG(4, "Hải Phòng"),
    CANTHO(5, "Cần Thơ"),
    BINHDUONG(6, "Bình Dương"),
    DONGNAI(7, "Đồng Nai"),
    KHANHHOA(8, "Khánh Hòa"),
    LONGAN(9, "Long An"),
    BRVT(10, "Bà Rịa - Vũng Tàu"),
    DAKLAK(11, "Đăk Lăk"),
    BINHTHUAN(12, "Bình Thuận"),
    LAMDONG(13, "Lâm Đồng"),
    HUE(14, "Thừa Thiên Huế"),
    QUANGNINH(15, "Quảng Ninh"),
    BACNINH(16, "Bắc Ninh"),
    HAIDUONG(17, "Hải Dương"),
    NGHEAN(18, "Nghệ An"),
    THANHHOA(19, "Thanh Hóa"),
    GIALAI(20, "Gia Lai"),
    BINHPHUOC(21, "Bình Phước"),
    HUNGYEN(22, "Hưng Yên"),
    BINHDINH(23, "Bình Định"),
    TIENGIANG(24, "Tiền Giang"),
    THAIBINH(25, "Thái Bình"),
    BACGIANG(26, "Bắc Giang"),
    HOABINH(27, "Hòa Bình"),
    ANGIANG(28, "An Giang"),
    VINHPHUC(29, "Vĩnh Phúc"),
    TAYNINH(30, "Tây Ninh"),
    THAINGUYEN(31, "Thái Nguyên"),
    LAOCAI(32, "Lào Cai"),
    NAMDINH(33, "Nam Định"),
    QUANGNGAI(34, "Quãng Ngãi"),
    BENTRE(35, "Ben Tre"),
    DAKNONG(36, "Đăk Nông"),
    CAMAU(37, "Cà Mau"),
    VINHLONG(38, "Vĩnh Long"),
    NINHBINH(39, "Ninh Bình"),
    PHUTHO(40, "Phú Thọ"),
    NINHTHUAN(41, "Ninh Thuận"),
    PHUYEN(42, "Phú Yên"),
    HANAM(43, "Hà Nam"),
    HATINH(44, "Hà Tĩnh"),
    DONGTHAP(45, "Đồng Tháp"),
    SOCTRANG(46, "Sóc Trăng"),
    KONTUM(47, "Kon Tum"),
    QUANGBINH(48, "Quãng Bình"),
    QUANGTRI(49, "Quãng Trị"),
    TRAVINH(50, "Trà Vinh"),
    HAUGIANG(51, "Hậu Giang"),
    SONLA(52, "Sơn La"),
    BACLIEU(53, "Bạc Liêu"),
    YENBAI(54, "Yên Bái"),
    TUYENQUANG(55, "Tuyên Quang"),
    DIENBIEN(56, "Điện Biên"),
    LAICHAU(57, "Lai Châu"),
    LANGSON(58, "Lạng Sơn"),
    HAGIANG(59, "Hậu Giang"),
    BACCAN(60, "Bắc Cạn"),
    CAOBANG(61, "Cao Bằng");

    private final int value;
    private final String displayValue;

    private ProvinceCity(int value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public static ProvinceCity fromValue(int value) {
        for (ProvinceCity type : ProvinceCity.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }

    public static Map<Integer,String> toHashMap(){
        Map<Integer,String> map = new HashMap();
        for(ProvinceCity type : ProvinceCity.values()){
            map.put(type.getValue(),type.displayValue);
        }
        return map;
    }
}
