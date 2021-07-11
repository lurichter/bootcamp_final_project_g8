package com.mercadolibre.group8_bootcamp_finalproject.integration;

import com.mercadolibre.group8_bootcamp_finalproject.exceptions.BatchNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class DueDateIntegrationTest extends ControllerTest{

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    String badRequestResponse;

    String token;

    String loginRequest = "{\n" +
            "    \"username\" : \"operador1@mercadolivre.com\",\n" +
            "    \"password\" : \"123456\"\n" +
            "}";

    @BeforeEach
    void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();

        badRequestResponse = "Bad Request Exception. There are no batches with the due date between the given range";

        MvcResult mvcResult = mockMvc.perform(
                post("/api/v1/sign-in")
                        .characterEncoding("UTF-8")
                        .content(loginRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json;charset=UTF-8"))
                .andReturn();

        JacksonJsonParser jsonParser = new JacksonJsonParser();

        token = jsonParser.parseMap(mvcResult.getResponse().getContentAsString()).get("token").toString();

    }

    @Test
    public void shouldReturnStatusCode200IfUserIsAuthenticated() throws Exception {
        mockMvc.perform(get
                ("/api/v1/fresh-products/due-date/list/{daysQuantity}", 465)
                .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusCode403ForbiddenIfUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/fresh-products/due-date/list", -1000)
                .accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnStatus500IfCategoryNameIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get
                ("/api/v1/fresh-products/due-date/list/{daysQuantity}", 465)
                .param("productCategory", "TEST")
                .param("order", "dueDate_asc")
                .header("authorization", token)
                .accept(MediaType.ALL))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnListOfBatchesOrderedByDescDueDateAndFilteredByCategoryName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get
                ("/api/v1/fresh-products/due-date/list/{daysQuantity}", 465)
                .param("productCategory", "FS")
                .header("authorization", token)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock[0].sectionId").value(1))
                .andExpect(jsonPath("$.batchStock[0].batchNumber").value("TESTE2"))
                .andExpect(jsonPath("$.batchStock[0].productId").value(1))
                .andExpect(jsonPath("$.batchStock[0].productCategory").value("Fresh"))
                .andExpect(jsonPath("$.batchStock[0].dueDate").value("2022-10-02"))
                .andExpect(jsonPath("$.batchStock[0].quantity").value(17));
    }

    @Test
    public void shouldReturnListOfBatchesOrderedByAscDueDateAndFilteredByCategoryName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get
                ("/api/v1/fresh-products/due-date/list/{daysQuantity}", 465)
                .param("productCategory", "FS")
                .param("order", "dueDate_asc")
                .header("authorization", token)
                .accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock[0].sectionId").value(1))
                .andExpect(jsonPath("$.batchStock[0].batchNumber").value("TESTE4"))
                .andExpect(jsonPath("$.batchStock[0].productId").value(2))
                .andExpect(jsonPath("$.batchStock[0].productCategory").value("Fresh"))
                .andExpect(jsonPath("$.batchStock[0].dueDate").value("2022-07-12"))
                .andExpect(jsonPath("$.batchStock[0].quantity").value(23));
    }

    @Test
    void shouldReturnBatchNotFoundExceptionWhenNoBatchIsFoundedWithTheDueDateRangeSpecified() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get
                ("/api/v1/fresh-products/due-date/list/{daysQuantity}", -1000)
                .header("authorization", token)
                .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BatchNotFoundException))
                .andExpect(result -> assertEquals(badRequestResponse + ".", result.getResolvedException().getMessage()));;
    }

    @Test

    void shouldReturnBatchNotFoundExceptionWhenNoBatchIsFoundedWithTheDueDateRangeAndCategorySpecified() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get
                ("/api/v1/fresh-products/due-date/list/{daysQuantity}", 100)
                .header("authorization", token)
                .param("productCategory", "FF")
                .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BatchNotFoundException))
                .andExpect(result -> assertEquals(
                        badRequestResponse + " and the category selected.", result.getResolvedException().getMessage()));;
    }

}

