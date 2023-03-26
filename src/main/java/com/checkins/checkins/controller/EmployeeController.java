package com.checkins.checkins.controller;

import com.checkins.checkins.dto.EmployeeRequest;
import com.checkins.checkins.entity.EmployeeEntity;
import com.checkins.checkins.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")


public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
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

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeEntity>> getAll() {
        List<EmployeeEntity> allEmployee;
        allEmployee = employeeService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allEmployee);
    }

    @PostMapping("/add")
    public ResponseEntity<EmployeeEntity> add(@RequestBody @Valid EmployeeRequest employeeRequest) {
        Integer employID = employeeService.createEmployee(employeeRequest);
        EmployeeEntity employeeEntity = employeeService.getEmployee(employID);
        return ResponseEntity.status(HttpStatus.OK).body(employeeEntity);
    }

    @PutMapping("/update")
    public ResponseEntity<EmployeeEntity> update(@RequestBody EmployeeRequest employeeRequest) {
//        EmployeeEntity employeeEntity = employeeService.getEmployee(employeeRequest.getId());
//        if (employeeEntity == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        employeeEntity = employeeService.updateEmployee(employeeRequest);
//        return ResponseEntity.status(HttpStatus.OK).body(employeeEntity);

        //如果值不是null，就做.....；如果值是null就回傳NOT_FOUND
        return Optional.ofNullable(employeeService.getEmployee(employeeRequest.getId()))
                .map(employeeEntity->{
                    employeeService.updateEmployee(employeeRequest);
                    return ResponseEntity.status(HttpStatus.OK).body(employeeEntity);
                }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }//update

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}//EmployeeController



