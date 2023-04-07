package com.checkins.checkins.enums;

public enum PositionEnum {
    //    //組長
//    TEAMLEADER,
//    ENGINEER,
//    ASSISTANT,
//    //董事長
//    CHAIRMAN,
//    //總裁
//    PRESIDENT,
//    MANAGER,
//    ADMINISTRATOR,
//    //行銷
//    MARKETING,
//    //會計
//    ACCOUNTANT
    TeamLeader,
    Engineer,
    Assistant,
    //董事長
    Chairman,
    //總裁
    President,
    Manager,
    Administrator,
    //行銷
    Marketing,
    //會計
    Accountant;

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
