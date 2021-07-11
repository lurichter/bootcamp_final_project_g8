package com.mercadolibre.group8_bootcamp_finalproject.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseTotalProductDTO;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class WarehouseControllerIntegrationTest extends ControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String token;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    WarehouseProductListDTO warehouseProductListDTO;

    @InjectMocks
    WarehouseTotalProductDTO warehouseTotalProductDTO;

    private String loginRequest = "{\n" +
            "    \"username\" : \"operador1@mercadolivre.com\",\n" +
            "    \"password\" : \"123456\"\n" +
            "}";

    @BeforeEach
    void setup() throws Exception {
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

         // mocking test

        this.warehouseProductListDTO.setProductId(1L);

        this.warehouseTotalProductDTO.setWarehouseCode(1L);
        this.warehouseTotalProductDTO.setTotalQuantity(42L);

        this.warehouseProductListDTO.setWarehouses(Arrays.asList(this.warehouseTotalProductDTO));

    }

    @Test
    void shouldReturnStatusCodeOkFromAllProductsFromWarehouseById () throws Exception {

        this.mockMvc
                .perform(get("/api/v1/fresh-products/warehouse/1")
                        .header("Authorization", this.token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatusCodeNotFoundFromAllProductsFromWarehouseById () throws Exception {
        this.mockMvc
                .perform(get("/api/v1/fresh-products/warehouse/9")
                        .header("Authorization", this.token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnCorrectProductQuantityFromWarehouseById () throws Exception {

        Long allProductQuantitySum = this.warehouseProductListDTO
                .getWarehouses()
                .stream().mapToLong(WarehouseTotalProductDTO::getTotalQuantity).sum();


        this.mockMvc.perform(get("/api/v1/fresh-products/warehouse/1")
                .header("Authorization", this.token)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouses[0].totalQuantity").value(allProductQuantitySum.intValue()));
    }

    @Test
    void shouldReturnIncorrectProductQuantityFromWarehouseById () throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/fresh-products/warehouse/1")
                .header("Authorization", this.token)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        WarehouseProductListDTO warehouseProductListResponse = objectMapper.readValue(contentAsString, WarehouseProductListDTO.class);

        Assert.assertNotEquals(java.util.Optional.ofNullable(warehouseProductListResponse.getWarehouses().get(0).getTotalQuantity()), 12L);
    }

    @Test
    void shouldReturnAllProductFromWarehouseById () throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(get("/api/v1/fresh-products/warehouse/1")
                .header("Authorization", this.token))
                .andDo(print())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        objectMapper.readValue(contentAsString, WarehouseProductListDTO.class);

    }
}
