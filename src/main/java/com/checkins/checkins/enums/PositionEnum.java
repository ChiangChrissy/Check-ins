package com.checkins.checkins.enums;

public enum PositionEnum {
    //組長
    TEAMLEADER,
    ENGINEER,
    ASSISTANT,
    //董事長
    CHAIRMAN,
    //總裁
    PRESIDENT,
    MANAGER,
    ADMINISTRATOR,
    //行銷
    MARKETING,
    //會計
    ACCOUNTANT;

    public static String getEnum(String value) {
        if (value == null || value.length() < 1) {
            return null;
        }
        for (PositionEnum positionEnum : values()) {
            if (positionEnum.name().equalsIgnoreCase(value))
                return positionEnum.toString();
        }
        return null;
    }


}
