package com.checkins.checkins.request;

import com.checkins.checkins.enums.PositionEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EmployeeRequest {

    private Integer id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String phone;
    @NotNull
    private String position;
    @NotNull
    private Integer salary;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}

