package com.checkins.checkins.request;

import com.checkins.checkins.enums.AuthorityEnum;
import com.checkins.checkins.enums.PositionEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EmployeeRequest {

    private Integer id;
    @NotEmpty
    private String name;
    @NotNull
    private String position;
    @NotNull
    private Integer salary;
    @NotNull
    private Integer age;
    @NotNull
    private String passwd;
    @NotNull
    private String auth;


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

    public PositionEnum getPosition() {
        return PositionEnum.getEnum(position);
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public AuthorityEnum getAuth() {
        return AuthorityEnum.getEnum(auth);
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}

