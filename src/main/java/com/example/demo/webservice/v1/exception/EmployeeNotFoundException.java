package com.example.demo.webservice.v1.exception;

public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException(String id) {
        super(String.format("Employee (ID %s) not found.", id));
    }

}
