package com.web.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum DirectionOfHouse {
    KXD(-1, "KHÔNG XÁC ĐỊNH"),
    EAST(1, "Đông"),
    WEST(2, "Tây"),
    SOUTH(3, "Nam"),
    NORTH(4, "Bắc"),
    NORTHEAST(5, "Đông - Bắc"),
    NORTHWEST(6, "Tây - Bắc"),
    SOUTHEAST(7, "Đông - Nam"),
    SOUTHWEST(8, "Tây - Nam");

    private final int value;
    private final String displayValue;

    DirectionOfHouse(int value, String displayValue) {
        this.value = value;
        this.displayValue = displayValue;
    }

    public int getValue() { return value; }

    public String getDisplayValue() { return displayValue; }

    public static DirectionOfHouse fromValue(int value) {
        for(DirectionOfHouse direction : DirectionOfHouse.values()) {
            if(direction.getValue() == value){
                return direction;
            }
        }
        return null;
    }

    public static Map<Integer, String> toHashMap() {
        Map<Integer, String> directions = new HashMap<>();
        for(DirectionOfHouse direction : DirectionOfHouse.values()){
            directions.put(direction.getValue(), direction.getDisplayValue());
        }
        return directions;
    }
}
