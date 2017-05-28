package com.example.demo.repository.v1;

import com.example.demo.model.v1.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> { }
