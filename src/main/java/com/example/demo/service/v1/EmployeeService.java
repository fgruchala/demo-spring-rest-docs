package com.example.demo.service.v1;

import com.example.demo.model.v1.Employee;
import com.example.demo.repository.v1.EmployeeRepository;
import com.example.demo.webservice.v1.exception.EmployeeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> findAll() throws Exception {
        LOG.info("Retrieving all employees ...");

        List<Employee> employees = employeeRepository.findAll();

        LOG.debug(employees.toString());
        LOG.info("{} employee(s) retrieved.", employees.size());

        return employees;
    }

    public Employee findOne(String id) throws EmployeeNotFoundException {
        LOG.info("Retrieving employee (ID {}) ...", id);

        Employee employee = employeeRepository.findOne(id);

        if(employee == null) {
            throw new EmployeeNotFoundException(id);
        }

        LOG.debug(employee.toString());
        LOG.info("Employee (ID {}) retrieved.", employee.getId());

        return employee;
    }

    public Employee insert(Employee employee) throws Exception {
        LOG.info("Inserting new employee ...");

        employee = employeeRepository.save(employee);

        LOG.info("New employee (ID {}) inserted.", employee.getId());

        return employee;
    }

    public Employee update(String id, Employee employee) throws EmployeeNotFoundException {
        LOG.info("Updating employee (ID {}) ...", id);

        Employee employeeToUpdate = this.findOne(id);
        employeeRepository.delete(employeeToUpdate);
        employee.setId(id);
        employeeRepository.save(employee);

        LOG.info("Employee (ID {}) updated.", id);

        return employee;
    }

    public void delete(String id) throws EmployeeNotFoundException {
        LOG.info("Deleting employee (ID {}) ...", id);

        Employee employee = this.findOne(id);
        employeeRepository.delete(employee);

        LOG.info("Employee (ID {}) deleted.", id);
    }


}
