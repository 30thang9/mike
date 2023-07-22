package com.nth.mike.service.impl;

import com.nth.mike.entity.Employee;
import com.nth.mike.repository.EmployeeRepo;
import com.nth.mike.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepo employeeRepo;

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepo.findById(id).orElse(null);
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepo.save(employee);
    }

    @Override
    public Long deleteById(Long id) {
        try {
            employeeRepo.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> findBY() {
        return employeeRepo.findBY();
    }
}
