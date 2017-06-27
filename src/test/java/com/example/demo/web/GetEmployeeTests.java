package com.example.demo.web;

import com.example.demo.webservice.v1.exception.EmployeeNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
public class GetEmployeeTests extends AbstractEmployeeWebService {

    @Test
    public void get_ById_OK() throws Exception {
        // GIVEN
        BDDMockito
                .given(employeeService.findOne(employee1.getId()))
                .willReturn(employee1);

        // WHEN
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URL + "/{id}", employee1.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(employee1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.is(employee1.getFirstname())));
    }

    @Test
    public void get_ById_NotFound_KO() throws Exception {
        // GIVEN
        String id = "99";

        BDDMockito
                .given(employeeService.findOne(id))
                .willThrow(new EmployeeNotFoundException(id));

        // WHEN
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URL + "/{id}", id)
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("Employee (ID 99) not found.")));
    }

}
