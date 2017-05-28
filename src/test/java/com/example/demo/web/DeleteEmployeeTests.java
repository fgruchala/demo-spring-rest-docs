package com.example.demo.web;

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
                RestDocumentationRequestBuilders
                        .delete(URL + "/{id}", employee1.getId())
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andDo(MockMvcRestDocumentation.document(
                        "{class-name}/{method-name}",
                        RequestDocumentation.pathParameters(parameterDescriptorsEmployeeId)));

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
                RestDocumentationRequestBuilders
                        .delete(URL + "/{id}", id)
                        .accept(MediaType.APPLICATION_JSON_UTF8));

        // THEN
        resultActions
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Matchers.is("Employee (ID 99) not found.")))
                .andDo(MockMvcRestDocumentation.document(
                        "{class-name}/{method-name}",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        RequestDocumentation.pathParameters(parameterDescriptorsEmployeeId),
                        PayloadDocumentation.responseFields(fieldDescriptorsSimpleError)));
    }

}
