package com.mercadolibre.group8_bootcamp_finalproject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.LoginRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.AccountResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AllArgsConstructor
public class LoginUtil {

    private static final LoginRequestDTO loginOperatorRequest = new LoginRequestDTO("operador1@mercadolivre.com", "123456");
    private static final LoginRequestDTO loginBuyerRequest = new LoginRequestDTO("comprador1@gmail.com", "123456");

    public static ResponseEntity<AccountResponseDTO> login(TestRestTemplate testRestTemplate, LoginRequestDTO loginRequest) {
        return testRestTemplate.postForEntity("/api/v1/sign-in",
                loginRequest, AccountResponseDTO.class);
    }

    public static String login(MockMvc mockMvc, LoginRequestDTO loginRequest) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                post("/api/v1/sign-in")
                        .characterEncoding("UTF-8")
                        .content(new ObjectMapper().writeValueAsString(loginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json;charset=UTF-8"))
                .andReturn();
        return new JacksonJsonParser().parseMap(mvcResult.getResponse().getContentAsString()).get("token").toString();
    }

    public static ResponseEntity<AccountResponseDTO> loginAsOperator(TestRestTemplate testRestTemplate) {
        return login(testRestTemplate, loginOperatorRequest);
    }

    public static String loginAsOperator(MockMvc mockMvc) throws Exception {
        return login(mockMvc, loginOperatorRequest);
    }

    public static String loginAsBuyer(MockMvc mockMvc) throws Exception {
        return login(mockMvc, loginBuyerRequest);
    }

}
