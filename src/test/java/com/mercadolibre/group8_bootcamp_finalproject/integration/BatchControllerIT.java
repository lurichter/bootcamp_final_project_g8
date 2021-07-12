package com.mercadolibre.group8_bootcamp_finalproject.integration;

import com.mercadolibre.group8_bootcamp_finalproject.exceptions.SortUtilException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

class BatchControllerIT extends ControllerTest{

	MockMvc mockMvc;
	String token;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext)
				.apply(springSecurity()).build();

		token = LoginUtil.loginAsOperator(mockMvc);
	}

	@Test
	public void shouldReturnStatusCode200IfUserIsAuthenticated() throws Exception {
		mockMvc.perform(get
				("/api/v1/fresh-products/batch/list/{productId}", 1)
				.header("Authorization", token))
				.andExpect(status().isOk());
	}

	@Test
	public void shouldReturnStatusCode403ForbiddenIfUserIsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/api/v1/fresh-products/batch/list/{productId}", -1000)
				.accept(MediaType.ALL))
				.andExpect(status().isForbidden());
	}

	@Test
	public void shouldReturnStatus404IfProductNotExists() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get
				("/api/v1/fresh-products/batch/list/{productId}", -1)
				.header("Authorization", token)
				.accept(MediaType.ALL))
				.andExpect(status().isNotFound());
	}

	@Test
	public void shouldReturnListOfProductBatchesByProductId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get
				("/api/v1/fresh-products/batch/list/{productId}", 1)
				.param("productCategory", "FS")
				.header("Authorization", token)
				.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(1))
				.andExpect(jsonPath("$.batchStock[0].wareHouseId").value(1))
				.andExpect(jsonPath("$.batchStock[0].sectionId").value(1))
				.andExpect(jsonPath("$.batchStock[0].batchNumber").value("TESTE"))
				.andExpect(jsonPath("$.batchStock[0].currentQuantity").value(12))
				.andExpect(jsonPath("$.batchStock[0].dueDate").value("2022-12-27"));
	}

	@Test
	public void shouldReturnListOfBatchesOrderedByDescDueDate() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get
				("/api/v1/fresh-products/batch/list/{productId}", 1)
				.param("order", "dueDate_asc")
				.header("Authorization", token)
				.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(1))
				.andExpect(jsonPath("$.batchStock[0].batchNumber").value("TESTE3"))
				.andExpect(jsonPath("$.batchStock[1].batchNumber").value("TESTE2"));
	}

	@Test
	public void shouldReturnListOfBatchesOrderedByQuantityDesc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get
				("/api/v1/fresh-products/batch/list/{productId}", 1)
				.param("order", "quantity_desc")
				.header("Authorization", token)
				.accept(MediaType.ALL))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(1))
				.andExpect(jsonPath("$.batchStock[0].batchNumber").value("TESTE2"))
				.andExpect(jsonPath("$.batchStock[1].batchNumber").value("TESTE3"));
	}

	@Test
	public void shouldReturnStatus400WhenBadOrderFieldInformed() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get
				("/api/v1/fresh-products/batch/list/{productId}", 1)
				.param("order", "testttttt")
				.header("Authorization", token)
				.accept(MediaType.ALL))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof SortUtilException));
	}
}
