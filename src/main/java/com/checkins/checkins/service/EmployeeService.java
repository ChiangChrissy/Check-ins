package com.checkins.checkins.service;

import com.checkins.checkins.dto.EmployeeRequest;
import com.checkins.checkins.entity.EmployeeEntity;
import com.checkins.checkins.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
        //如果值存在，就回傳；若不存在，回傳null
        return employeeRepository.findById(id).orElse(null);
    }
    public List<EmployeeEntity> getAll(){
        List<EmployeeEntity> allData;
        allData = employeeRepository.findAll();
        return allData;
    }
    public Integer createEmployee(EmployeeRequest employeeRequest) {
        if(!employeeRequest.getPhone().matches("^09\\d{8}")){
            throw new IllegalArgumentException("手機格式不正確");
        }

        EmployeeEntity employeeEntity = new EmployeeEntity();
        createSetValues(employeeRequest, employeeEntity);
        employeeEntity = employeeRepository.save(employeeEntity);
        return employeeEntity.getId();
    }

    private static void createSetValues(EmployeeRequest employeeRequest, EmployeeEntity employeeEntity) {
        employeeEntity.setName(employeeRequest.getName());
        employeeEntity.setPhone(employeeRequest.getPhone());
        employeeEntity.setPosition(employeeRequest.getPosition());
    }
    private static void updateSetValues(EmployeeRequest employeeRequest, EmployeeEntity employeeEntity) {
        employeeEntity.setId(employeeRequest.getId());
        employeeEntity.setName(employeeRequest.getName());
        employeeEntity.setPhone(employeeRequest.getPhone());
        employeeEntity.setPosition(employeeRequest.getPosition());
    }

    public void deleteEmployee(Integer id){
        employeeRepository.deleteById(id);
    }

    public EmployeeEntity updateEmployee(EmployeeRequest employeeRequest){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        updateSetValues(employeeRequest,employeeEntity);
        employeeEntity = employeeRepository.save(employeeEntity);
        return employeeEntity;
    }
}//EmployeeService
