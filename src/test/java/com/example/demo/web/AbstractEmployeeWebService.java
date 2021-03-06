package com.example.demo.web;

import com.example.demo.model.v1.Employee;
import com.example.demo.service.v1.EmployeeService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(com.example.demo.webservice.v1.EmployeeWebService.class)
public abstract class AbstractEmployeeWebService {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected EmployeeService employeeService;

    protected static final String URL = "/api/v1/employee";

    protected Employee employee1;
    protected Employee employee2;

    @Before
    public void initData() {
        employee1 = new Employee();
        employee1.setId("1");
        employee1.setFirstname("Larry");
        employee1.setName("PAGE");

        employee2 = new Employee();
        employee2.setId("2");
        employee2.setFirstname("Sergueï");
        employee2.setName("BRIN");
    }

    protected String toJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(object);
    }

}
