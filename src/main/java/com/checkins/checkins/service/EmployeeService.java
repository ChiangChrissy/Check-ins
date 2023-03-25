package com.checkins.checkins.service;

import com.checkins.checkins.dto.EmployeeRequest;
import com.checkins.checkins.entity.EmployeeEntity;
import com.checkins.checkins.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
//get()：如果值存在就回傳這個值，否則就丟出 NoSuchElementException。
//Optional.of(id)方法將id包装成Optional物件。
//Optional.of(id).get()方法取得Optional物件中的值，此為id。
//employeeRepository.findById()如果沒有EmployeeEntity物件會為null
//employeeRepository.findById().get() 是用來取得EmployeeEntity物件，如果是null會報錯:java.util.NoSuchElementException
    public EmployeeEntity getEmployee(Integer id) {
//        return Optional.ofNullable(employeeRepository.findById(Optional.of(id).get()).get()).get();
        return employeeRepository.findById(id).orElse(null);
    }

    public Integer createEmployee(EmployeeRequest employeeRequest) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        setValues(employeeRequest, employeeEntity);
        employeeEntity = employeeRepository.save(employeeEntity);
        return employeeEntity.getId();
    }

    private static void setValues(EmployeeRequest employeeRequest, EmployeeEntity employeeEntity) {
        employeeEntity.setName(employeeRequest.getName());
        employeeEntity.setPhone(employeeRequest.getPhone());
        employeeEntity.setPosition(employeeRequest.getPosition());
    }


}//EmployeeService
