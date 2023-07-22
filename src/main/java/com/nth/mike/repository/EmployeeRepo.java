package com.nth.mike.repository;

import com.nth.mike.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    @Query(value = "SELECT * FROM employees WHERE name = ?1", nativeQuery = true)
    List<Employee> findBY();
}
