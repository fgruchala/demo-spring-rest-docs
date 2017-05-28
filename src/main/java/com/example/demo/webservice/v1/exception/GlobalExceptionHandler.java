package com.example.demo.webservice.v1.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    Map<String, String> simpleResponse;


    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, String> resourceNotFound(Exception e) {
        simpleResponse = new HashMap<>();
        simpleResponse.put("error", e.getMessage());

        LOG.warn(e.getMessage());
        return simpleResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> internalServerError(Exception e) {
        simpleResponse = new HashMap<>();
        simpleResponse.put("error", e.getMessage());

        LOG.error(e.getMessage());
        return simpleResponse;
    }

}
