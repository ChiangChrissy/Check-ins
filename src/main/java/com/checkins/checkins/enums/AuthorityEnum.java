package com.checkins.checkins.enums;

public enum AuthorityEnum {
    ADMIN,
    MANAGER,
    EMPLOYEE;

    public static AuthorityEnum getEnum(String value){
        if(value==null||value.length()<1){
            return null;
        }
        for (AuthorityEnum a:values()) {
            if (a.name().equalsIgnoreCase(value)){
                return a;
            }
        }
        return null;
    }
}
