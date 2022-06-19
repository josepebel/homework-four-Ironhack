package com.ironhack.edgeservice.controller.implementations;

import com.ironhack.edgeservice.controller.interfaces.EmployeeController;
import com.ironhack.edgeservice.service.interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class EmployeeControllerImpl implements EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employee-count/mean")
    @ResponseStatus(HttpStatus.OK)
    public String meanEmployeeCount() {
        return employeeService.meanEmployeeCount();
    }

    @GetMapping("/employee-count/median")
    @ResponseStatus(HttpStatus.OK)
    public String medianEmployeeCount() {
        return employeeService.medianEmployeeCount();
    }

    @GetMapping("/employee-count/max")
    @ResponseStatus(HttpStatus.OK)
    public String maxEmployeeCount() {
        return employeeService.maxEmployeeCount();
    }

    @GetMapping("/employee-count/min")
    public String minEmployeeCount() {
        return employeeService.minEmployeeCount();
    }
}
