package com.mercadolibre.group8_bootcamp_finalproject.integration;

import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.SellerNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.util.LoginUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class SellerControllerIntegrationTest extends ControllerTest{
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    String token;

    @BeforeEach
    void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();

        token = LoginUtil.loginAsOperator(mockMvc);
    }

    @Test
    public void shouldReturnStatusCode200IfUserIsAuthenticated() throws Exception {
        mockMvc.perform(get
                ("/api/v1/fresh-products/seller/list/{sellerId}", 1)
                .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnStatusCode403ForbiddenIfUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get
                ("/api/v1/fresh-products/seller/list/{sellerId}", 1)
                .accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnSellerNotFoundExceptionIfSellerIdIsInvalid() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get
                ("/api/v1/fresh-products/seller/list/{sellerId}", 465)
                .header("Authorization", token))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof SellerNotFoundException));
    }

    @Test
    public void shouldReturnProductNotFoundExceptionIfSellerDoesNotHaveProducts() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get
                ("/api/v1/fresh-products/seller/list/{sellerId}", 2)
                .header("Authorization", token))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException));
    }

}
