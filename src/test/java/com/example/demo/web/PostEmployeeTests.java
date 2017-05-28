package com.example.demo.web;

import com.example.demo.model.v1.Employee;
import com.example.demo.webservice.v1.exception.EmployeeNotFoundException;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
public class PostEmployeeTests extends AbstractEmployeeWebService {

    @Test
    public void create_OK() throws Exception {
        // GIVEN
        Employee employeeCreated = employee1;

        Employee employeeToCreate = new Employee();
        employeeToCreate.setName(employeeCreated.getName());
        employeeToCreate.setFirstname(employeeCreated.getFirstname());

        BDDMockito
                .given(employeeService.insert(employeeToCreate))
                .willReturn(employeeCreated);

        // WHEN
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(URL)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(employeeToCreate))
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(employeeCreated.getId())))
                .andDo(MockMvcRestDocumentation.document(
                        "{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        PayloadDocumentation.requestFields(fieldDescriptorsEmployeeCreate),
                        PayloadDocumentation.responseFields(fieldDescriptorsEmployeeOnlyId)));
    }

    @Test
    public void update_ById_OK() throws Exception {
        // GIVEN
        Employee employeeUpdated = employee1;

        Employee employeeToUpdate = new Employee();
        employeeToUpdate.setName("PAGE MOD");
        employeeToUpdate.setFirstname(employeeUpdated.getFirstname());

        BDDMockito
                .given(employeeService.update(employeeUpdated.getId(), employeeToUpdate))
                .willReturn(employeeUpdated);

        // WHEN
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post(URL + "/{id}", employeeUpdated.getId())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(employeeToUpdate))
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(employeeUpdated.getId())))
                .andDo(MockMvcRestDocumentation.document(
                        "{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.pathParameters(parameterDescriptorsEmployeeId),
                        PayloadDocumentation.requestFields(fieldDescriptorsEmployeeUpdate),
                        PayloadDocumentation.responseFields(fieldDescriptorsEmployeeOnlyId)));
    }

    @Test
    public void update_ById_NotFound_KO() throws Exception {
        // GIVEN
        String id = "99";

        Employee employeeToUpdate = employee1;
        employee1.setName("PAGE MOD");

        BDDMockito
                .given(employeeService.update(id, employeeToUpdate))
                .willThrow(new EmployeeNotFoundException(id));

        // WHEN
        ResultActions resultActions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post(URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(toJson(employeeToUpdate))
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("Employee (ID 99) not found.")))
                .andDo(MockMvcRestDocumentation.document(
                        "{class-name}/{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.pathParameters(parameterDescriptorsEmployeeId),
                        PayloadDocumentation.responseFields(fieldDescriptorsSimpleError)));
    }

}
