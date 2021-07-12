package com.mercadolibre.group8_bootcamp_finalproject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.util.PurchaseOrderCreator;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtil;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtilUpdated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class PurchaseOrderIntegrationTest extends ControllerTest{

    private TestObjectsUtilUpdated testObjectsUtil;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String token;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    ProductListDTO productListDTO;

    private String loginRequest = "{\n" +
            "    \"username\" : \"operador1@mercadolivre.com\",\n" +
            "    \"password\" : \"123456\"\n" +
            "}";

    @BeforeEach
    void setup() throws Exception{
        testObjectsUtil = new TestObjectsUtilUpdated();

        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();

        MvcResult mvcResult = mockMvc.perform(
                post("/api/v1/sign-in")
                        .characterEncoding("UTF-8")
                        .content(this.loginRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json;charset=UTF-8"))
                .andReturn();

        JacksonJsonParser jsonParser = new JacksonJsonParser();

        token = jsonParser.parseMap(mvcResult.getResponse().getContentAsString()).get("token").toString();

        Set<ProductDTO> productDTOS = this.testObjectsUtil.getProductDTOS();

        productListDTO.setProducts(productDTOS);

    }

    @Test
    public void shouldReturnStatusCode403ForbiddenIfUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/fresh-products/orders/{idOrder}", 1)
                .accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnStatusCodeOkFromGetAllProductsFromPurchaseOrder() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/fresh-products/orders/{idOrder}", 1)
                        .header("Authorization", this.token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundFromGetAllProductsFromPurchaseOrderWhenPurchaseOrderIdIsInvalid() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/fresh-products/orders/{idOrder}", 200)
                        .header("authorization", this.token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllProductsFromPurchaseOrder() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/fresh-products/orders/{idOrder}", 1)
                        .header("authorization", this.token)
                        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].id").value(1))
                .andExpect(jsonPath("$.products[0].name").value("Tomate Caqui unidade"))
                .andExpect(jsonPath("$.products[0].description").value("Uma unidade de tomate Caqui"))
                .andExpect(jsonPath("$.products[0].minTemperature").value(8.0))
                .andExpect(jsonPath("$.products[0].maxTemperature").value(13.0))
                .andExpect(jsonPath("$.products[0].price").value(2.39))
                .andDo(print())
                .andReturn();
    }

    @Test
    void shouldReturnPriceFromNewPurchaseOrder() throws Exception {

        this.mockMvc
                .perform(post("/api/v1/fresh-products/orders")
                        .header("authorization", this.token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PurchaseOrderCreator.createValidPurchaseOrderRequest()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalPrice").value(5.49))
                .andDo(print())
                .andReturn();
    }

    @Test
    void shouldReturnProductOutStockNotFoundException() throws Exception {

        this.mockMvc
                .perform(post("/api/v1/fresh-products/orders")
                        .header("authorization", this.token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PurchaseOrderCreator.createInvalidPurchaseOrderRequest()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnPriceFromUpdatedPurchaseOrder() throws Exception {

        this.mockMvc
                .perform(put("/api/v1/fresh-products/orders/{idOrder}", 1)
                        .header("authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PurchaseOrderCreator.createValidPurchaseOrderUpdateRequest()))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPrice").value(42.58))
                .andDo(print())
                .andReturn();
    }

}
