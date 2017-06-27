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
public class DeleteEmployeeTests extends AbstractEmployeeWebService {

    @Test
    public void delete_ById_OK() throws Exception {
        // GIVEN
        BDDMockito
                .willDoNothing()
                .given(employeeService)
                .delete(employee1.getId());

        // WHEN
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .delete(URL + "/{id}", employee1.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));

    }

    @Test
    public void delete_ById_NotFound_KO() throws Exception {
        // GIVEN
        String id = "99";

        BDDMockito
                .willThrow(new EmployeeNotFoundException(id))
                .given(employeeService)
                .delete(id);

        // WHEN
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .delete(URL + "/{id}", id)
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("Employee (ID 99) not found.")));
    }

}
