package com.SkyPro.SpringAndMocckito.Service;

import com.SkyPro.SpringAndMocckito.exeption.EmployeeAlreadyAddedException;
import com.SkyPro.SpringAndMocckito.exeption.EmployeeNotFoundException;
import com.SkyPro.SpringAndMocckito.exeption.EmployeeStorageIsFullException;
import com.SkyPro.SpringAndMocckito.model.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class EmployeeService {

    private static final int SIZE=10;
    private final Map<String, Employee> employees = new HashMap<>();

    private final ValidatorService validatorService;

    public EmployeeService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    private String getKey(String name, String surname){
        return name + "|" + surname;}

    public Employee addEmployee(String name,
                                String surname,
                                int department,
                                double salary) {
        Employee employee = new Employee(
                validatorService.validateName(name),
                validatorService.validateSurname(surname),
                department,
                salary);
        String key = getKey(employee.getName(), employee.getSurname());
        if (employees.containsKey(key)){
            throw new EmployeeAlreadyAddedException();
        }
        if (employees.size()<SIZE){
            employees.put(key,employee);
            return employee;
        }
        throw new EmployeeStorageIsFullException();


    }
    public Employee removeEmployee(String name, String surname) {
        String key = getKey(name, surname);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }

        return employees.remove(key);

    }
    public Employee findEmployee(String name, String surname) {
        String key = getKey(name, surname);
        if (!employees.containsKey(key)) {
            throw new EmployeeNotFoundException();
        }
        return employees.get(key);


    }

    public List<Employee> getAll() {
        return new ArrayList<>(employees.values());
    }



}
