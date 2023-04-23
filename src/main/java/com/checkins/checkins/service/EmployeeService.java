package com.checkins.checkins.service;

import com.checkins.checkins.entity.EmployeeEntity;
import com.checkins.checkins.enums.AuthorityEnum;
import com.checkins.checkins.enums.PositionEnum;
import com.checkins.checkins.repo.EmployeeRepository;
import com.checkins.checkins.request.EmployeeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import static com.checkins.checkins.enums.PositionEnum.getEnum;
import static java.lang.Integer.parseInt;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    private final static Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeEntity getEmployee(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<EmployeeEntity> getAll() {
        return employeeRepository.findAll();
    }

    public Integer createEmployee(EmployeeRequest employeeRequest) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        createSetValues(employeeRequest, employeeEntity);
        employeeEntity = employeeRepository.save(employeeEntity);
        return employeeEntity.getId();
    }

    public String deleteEmployee(Integer id) {
        try {
            employeeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.warn("No EmployeeEntity entity with id " + id + " exists!");
            return "Success.";
        }
        return "Success.";
    }

    public Integer updateEmployee(EmployeeRequest employeeRequest) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        try {
            updateSetValues(employeeRequest, employeeEntity);
            employeeEntity = employeeRepository.save(employeeEntity);
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.error(String.valueOf(illegalArgumentException.getMessage()));
        } catch (Exception e) {
            LOGGER.error("Column does not allow nulls. UPDATE fails.");
            return null;
        }
        return employeeEntity.getId();
    }//updateEmployee

    public List<EmployeeEntity> getEmployeeByPosition(String position) {
        List<EmployeeEntity> allEmployeeList = getAll();
        return allEmployeeList.stream().filter(
                        a -> getEnum(position)==getEnum(a.getPosition()))
                .collect(Collectors.toList());
    }

    public List<EmployeeEntity> unifiedSalaryAdjustment(Integer modify) {
        List<EmployeeEntity> allEmployeeList = getAll();
        return allEmployeeList.stream()
                .map(employee -> {
                    int adjustedSalary = employee.getSalary() + modify;
                    employee.setSalary(adjustedSalary);
                    employeeRepository.save(employee);//將結果寫進db
                    return employee;
                }).collect(Collectors.toList());
    }

    public Integer employeeAgeAverage() {
        List<EmployeeEntity> allEmployeeList = getAll();
        // 算出員工人數
        long number = allEmployeeList.size();
        // 回傳員工年紀平均
        return allEmployeeList.stream()
//                .mapToInt(em->em.getAge())
                .mapToInt(EmployeeEntity::getAge)
//                .reduce((sum,age)->sum+age)
                .reduce(Integer::sum)
//                .getAsInt()/(int) number
                .orElse(0)/(int) number;
    }

//    private static void checkPhoneDigit(EmployeeRequest employeeRequest) {
//        if (!employeeRequest.getPhone().matches("^09\\d{8}")) {
//            throw new IllegalArgumentException("手機格式不正確");
//        }
//    }

    private static void createSetValues(EmployeeRequest employeeRequest, EmployeeEntity employeeEntity) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        employeeEntity.setName(employeeRequest.getName());
        employeeEntity.setPosition(employeeRequest.getPosition().name());
        employeeEntity.setSalary(employeeRequest.getSalary());
        employeeEntity.setAge(employeeRequest.getAge());
        employeeEntity.setUserPwd(bCryptPasswordEncoder.encode(employeeRequest.getPasswd()));
        employeeEntity.setAuth(employeeRequest.getAuth().name());
    }

    private static void updateSetValues(EmployeeRequest employeeRequest, EmployeeEntity employeeEntity) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        employeeEntity.setId(employeeRequest.getId());
        employeeEntity.setName(employeeRequest.getName());
        employeeEntity.setPosition(employeeRequest.getPosition().name());
        employeeEntity.setSalary(employeeRequest.getSalary());
        employeeEntity.setAge(employeeRequest.getAge());
        employeeEntity.setUserPwd(bCryptPasswordEncoder.encode(employeeRequest.getPasswd()));
        employeeEntity.setAuth(employeeRequest.getAuth().name());
    }


}//EmployeeService
