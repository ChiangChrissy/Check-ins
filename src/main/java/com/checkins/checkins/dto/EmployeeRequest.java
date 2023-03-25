package com.checkins.checkins.dto;

import com.checkins.checkins.constant.Position;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EmployeeRequest {
    @NotEmpty
    private String name;
    @NotNull
    private String phone;
    @NotNull
    private Position position;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}

