package com.example.demo.webservice.v1;

import com.example.demo.model.v1.Employee;
import com.example.demo.service.v1.EmployeeService;
import com.example.demo.webservice.v1.view.Detail;
import com.example.demo.webservice.v1.view.OnlyId;
import com.example.demo.webservice.v1.view.Summary;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeWebService {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping
    @JsonView(Summary.class)
    public List<Employee> findAll() throws Exception {
        return employeeService.findAll();
    }

    @RequestMapping("/{id}")
    @JsonView(Detail.class)
    public Employee findOne(@PathVariable("id") String id) throws Exception {
        return employeeService.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @JsonView(OnlyId.class)
    public Employee insert(@RequestBody Employee employee) throws Exception {
        return employeeService.insert(employee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @JsonView(OnlyId.class)
    public Employee update(
            @PathVariable("id") String id,
            @RequestBody Employee employee) throws Exception {
        return employeeService.update(id, employee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") String id) throws Exception {
        employeeService.delete(id);
    }

}
