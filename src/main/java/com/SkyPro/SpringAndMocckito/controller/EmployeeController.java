package com.SkyPro.SpringAndMocckito.controller;

import com.SkyPro.SpringAndMocckito.Service.EmployeeService;
import com.SkyPro.SpringAndMocckito.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")

public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping("/add")
    public Employee add(@RequestParam("firstName")String name,
                        @RequestParam("lastName")String surname,
                        @RequestParam("departmentid")int department,
                        @RequestParam double salary){
        return employeeService.addEmployee(name, surname, department, salary);

    }

    @GetMapping("/remove")
    public Employee remove(@RequestParam("firstName")String name,
                        @RequestParam("lastName")String surname){
        return employeeService.removeEmployee(name, surname);

    }

    @GetMapping("/find")
    public Employee find(@RequestParam("firstName")String name,
                        @RequestParam("lastName")String surname){
        return employeeService.findEmployee(name, surname);

    }

    @GetMapping
    public List<Employee> getALl(){
        return employeeService.getAll();

    }


}
