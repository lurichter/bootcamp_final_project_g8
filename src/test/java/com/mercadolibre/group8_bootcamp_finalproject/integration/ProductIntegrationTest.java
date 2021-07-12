package com.mercadolibre.group8_bootcamp_finalproject.integration;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductListDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class ProductIntegrationTest extends ControllerTest{

    private TestObjectsUtilUpdated testObjectsUtil;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String token;

    @InjectMocks
    ProductListDTO productListDTO;

    private String loginRequest = "{\n" +
            "    \"username\" : \"operador1@mercadolivre.com\",\n" +
            "    \"password\" : \"123456\"\n" +
            "}";

    @BeforeEach
    void setup() throws Exception {

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
        mockMvc.perform(get("/api/v1/fresh-products/")
                .accept(MediaType.ALL))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnStatusCodeOkFromGetAllProducts() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/fresh-products/")
                        .header("Authorization", this.token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAllProducts() throws Exception {
        this.mockMvc
                .perform(get("/api/v1/fresh-products/")
                        .header("authorization", this.token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].id").value(1))
                .andExpect(jsonPath("$.products[0].name").value("Tomate Caqui unidade"))
                .andExpect(jsonPath("$.products[0].description").value("Uma unidade de tomate Caqui"))
                .andExpect(jsonPath("$.products[0].minTemperature").value(8.0))
                .andExpect(jsonPath("$.products[0].maxTemperature").value(13.0))
                .andExpect(jsonPath("$.products[0].price").value(2.39))
                .andExpect(jsonPath("$.products[1].id").value(2))
                .andExpect(jsonPath("$.products[1].name").value("Banana nanica 6/un"))
                .andExpect(jsonPath("$.products[1].description").value("Um cacho com 6 bananas nanicas"))
                .andExpect(jsonPath("$.products[1].minTemperature").value(8.0))
                .andExpect(jsonPath("$.products[1].maxTemperature").value(13.0))
                .andExpect(jsonPath("$.products[1].price").value(3.1))
                .andDo(print())
                .andReturn();

    }

    @Test
    void shouldReturnAllProductsFilteringByCategoryFS() throws Exception {
        mockMvc.perform(get("/api/v1/fresh-products/list/{productCategory}", "FS")
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
    void shouldReturnAllProductsFilteringByCategoryRF() throws Exception {
        mockMvc.perform(get("/api/v1/fresh-products/list/{productCategory}", "RF")
                .header("authorization", this.token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].id").value(3))
                .andExpect(jsonPath("$.products[0].name").value("Alface Crespa Bem Querer 140g"))
                .andExpect(jsonPath("$.products[0].description").value("Um maço de alface crespa higienizado"))
                .andExpect(jsonPath("$.products[0].minTemperature").value(0.0))
                .andExpect(jsonPath("$.products[0].maxTemperature").value(3.0))
                .andExpect(jsonPath("$.products[0].price").value(3.98))
                .andDo(print())
                .andReturn();
    }

    @Test
    void shouldReturnAllProductsFilteringByCategoryFF() throws Exception {
        mockMvc.perform(get("/api/v1/fresh-products/list/{productCategory}", "FF")
                .header("authorization", this.token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].id").value(5))
                .andExpect(jsonPath("$.products[0].name").value("Torta de frango Catupiry congelada 700g"))
                .andExpect(jsonPath("$.products[0].description").value("Torta congelada com recheio de grango com Catupiry(original) cremoso"))
                .andExpect(jsonPath("$.products[0].minTemperature").value(-3.0))
                .andExpect(jsonPath("$.products[0].maxTemperature").value(4.0))
                .andExpect(jsonPath("$.products[0].price").value(22.4))
                .andDo(print())
                .andReturn();
    }

    @Test
    void shouldReturnBadRequestWhenCategoryNameIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/fresh-products/list/{productCategory}", "Eletronico")
                .header("authorization", this.token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
