package com.nth.mike.service;

import com.nth.mike.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();
    Employee findById(Long id);

    Employee save(Employee employee);
    Long deleteById(Long id);
    List<Employee> findBY();
}

