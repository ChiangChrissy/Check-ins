package com.checkins.checkins.controller;
import com.checkins.checkins.request.EmployeeRequest;
import com.checkins.checkins.entity.EmployeeEntity;
import com.checkins.checkins.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public EmployeeEntity getEmployee(@PathVariable Integer id) {
        return employeeService.getEmployee(id);
    }//getEmployee
    @GetMapping("/all")
    public List<EmployeeEntity> getAll() {
        return employeeService.getAll();
    }
    @PostMapping("/createEmployee")
    public EmployeeEntity createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return employeeService.getEmployee(employeeService.createEmployee(employeeRequest));
    }
    @PutMapping("/updateEmployee")
    public EmployeeEntity updateEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.getEmployee(employeeService.updateEmployee(employeeRequest));
    }//update
    @DeleteMapping("/removeEmployee/{id}")
    public String removeEmployee(@PathVariable Integer id) {
        return employeeService.deleteEmployee(id);
    }
    @PostMapping("/employeePosition/{position}")
    public List<EmployeeEntity> getEmployeePosition(@PathVariable String position){
        return employeeService.getEmployeeByPosition(position);
    }
    @PostMapping("/unifiedSalaryAdjustment/{modify}")
    public List<EmployeeEntity> unifiedSalaryAdjustment(@PathVariable Integer modify){
        return employeeService.unifiedSalaryAdjustment(modify);
    }
    @GetMapping("/averageAge")
    public Integer employeeAgeAverage(){return employeeService.employeeAgeAverage();}



}//EmployeeController



