package com.checkins.checkins.controller;

import com.checkins.checkins.dto.EmployeeRequest;
import com.checkins.checkins.entity.EmployeeEntity;
import com.checkins.checkins.repo.EmployeeRepository;
import com.checkins.checkins.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/employee")


public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/{id}")
    public ResponseEntity<EmployeeEntity> getEmployee(@PathVariable Integer id) {
//        Optional<EmployeeEntity> employeeEntity = Optional.ofNullable(employeeService.getEmployee(id));

//    if (employeeEntity.isPresent()) {
//        return ResponseEntity.status(HttpStatus.OK).body(employeeEntity.get());
//    } else {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }

        return Optional.ofNullable(employeeService.getEmployee(id))
                .map(employeeEntity -> ResponseEntity.status(HttpStatus.OK).body(employeeEntity))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());


    }//getEmployee

    @PostMapping("/add")
    public ResponseEntity<EmployeeEntity> add(@RequestBody @Valid EmployeeRequest employeeRequest) {
        Integer employID = employeeService.createEmployee(employeeRequest);
        EmployeeEntity employeeEntity = employeeService.getEmployee(employID);


        return ResponseEntity.status(HttpStatus.OK).body(employeeEntity);
    }


}//EmployeeController



