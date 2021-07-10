package com.mercadolibre.group8_bootcamp_finalproject.integration;

import com.mercadolibre.group8_bootcamp_finalproject.exceptions.BatchNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

//@WithMockUser(username = "operadorrrr1@mercadolivre.com", password = "12345678")
public class DueDateIntegrationTest  extends ControllerTest{
//    @Mock
//    IDueDateService dueDateService;
//
//    @Mock
//    DueDateController dueDateController;

    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    String badRequestResponse;

    @BeforeEach
    public void setup(){
//        mockMvc = standaloneSetup(dueDateController).build();
        mockMvc = webAppContextSetup(webApplicationContext).build();
        badRequestResponse = "Bad Request Exception. There are no batches with the due date between the given range";
    }

//    @Test
//    public void shouldReturnStatusCode200IfUserIsAuthenticated() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get
//                ("/api/v1/fresh-products/due-date/list/{daysQuantity}", "100")
//                .accept(MediaType.ALL))
//                .andExpect(status().isOk());
//    }

    @Test
    public void shouldReturnListOfBatchesOrderedByDueDateFilteredByCategoryName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get
                ("/api/v1/fresh-products/due-date/list/{daysQuantity}", "100")
                .param("productCategory", "FS")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.batchStock[0].sectionId").value(1))
//                .andExpect(jsonPath("$.batchStock[0].batchNumber").value("teste"))
//                .andExpect(jsonPath("$.batchStock[0].productId").value(2))
//                .andExpect(jsonPath("$.batchStock[0].productCategory").value("Fresh"))
//                .andExpect(jsonPath("$.batchStock[0].dueDate").value("2021-10-06"))
//                .andExpect(jsonPath("$.batchStock[0].quantity").value(12));
    }

    @Test
    void shouldReturnBatchNotFoundExceptionWhenNoBatchIsFoundedWithTheDueDateRangeSpecified() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get
                ("/api/v1/fresh-products/due-date/list/{daysQuantity}", 1)
                .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BatchNotFoundException))
                .andExpect(result -> assertEquals(badRequestResponse + ".", result.getResolvedException().getMessage()));;
    }

    @Test
    void shouldReturnBatchNotFoundExceptionWhenNoBatchIsFoundedWithTheDueDateRangeAndCategorySpecified() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get
                ("/api/v1/fresh-products/due-date/list/{daysQuantity}", 100)
                .param("productCategory", "FF")
                .accept(MediaType.ALL))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BatchNotFoundException))
                .andExpect(result -> assertEquals(badRequestResponse + " and the category selected.", result.getResolvedException().getMessage()));;
    }

//    @Test
//    @WithMockUser(username = "teste@mercadolivre.com", password = "teste")
//    public void shouldReturnStatusCode403ForbiddenIfUserIsNotAuthenticated() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/due-date/list/10")
//                .accept(MediaType.ALL))
//                .andExpect(status().isForbidden());
//    }

    //    @Test
//    @WithMockUser(username = "operador1@mercadolivre.com", password = "123456")
//        ResponseEntity<BatchStockDueDateListDTO> responseEntity = this.testRestTemplate.exchange(
//                "/api/v1/fresh-products/due-date/list/10",
//                HttpMethod.GET,
//                this.getDefaultRequestEntity(),
//                BatchStockDueDateListDTO.class);
//        Assertions.assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode());
//    }

}

