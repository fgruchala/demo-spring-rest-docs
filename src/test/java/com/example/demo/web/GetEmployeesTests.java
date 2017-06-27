package com.example.demo.web;

import com.example.demo.model.v1.Employee;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
public class GetEmployeesTests extends AbstractEmployeeWebService {

    @Test
    public void get_All_Zero_OK() throws Exception {
        // GIVEN
        BDDMockito
                .given(employeeService.findAll())
                .willReturn(new ArrayList<>());

        // WHEN
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URL)
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void get_All_Multi_OK() throws Exception {
        // GIVEN
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        BDDMockito
                .given(employeeService.findAll())
                .willReturn(employees);

        // WHEN
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URL)
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(employees.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(employee1.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(employee1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstname", Matchers.is(employee1.getFirstname())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(employee2.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(employee2.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstname", Matchers.is(employee2.getFirstname())));
    }

}
