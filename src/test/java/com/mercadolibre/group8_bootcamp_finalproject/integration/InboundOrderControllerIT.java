package com.mercadolibre.group8_bootcamp_finalproject.integration;

import com.mercadolibre.group8_bootcamp_finalproject.exceptions.BadRequestException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.UserIsNotAOperatorException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.WarehouseSectionCapabilityException;
import com.mercadolibre.group8_bootcamp_finalproject.util.InboundOrderCreator;
import com.mercadolibre.group8_bootcamp_finalproject.util.LoginUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

class InboundOrderControllerIT extends ControllerTest{

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
	void shouldReturnStatusCode403ForbiddenIfUserIsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/api/v1/fresh-products/inboundorder/")
				.accept(MediaType.ALL))
				.andExpect(status().isForbidden());
	}

	@Test
	void shouldReturnForbiddenExceptionIfUserIsNotAOperator() throws Exception {
		String token = LoginUtil.loginAsBuyer(mockMvc);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/inboundorder/")
				.content(objectMapper.writeValueAsString(InboundOrderCreator.createValidInboundOrderCreateRequest()))
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL))
				.andExpect(status().isForbidden())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof UserIsNotAOperatorException));
	}

	@Test
	void shouldReturnBadRequestExceptionIfBadSectionCategory() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/inboundorder/")
				.content(objectMapper.writeValueAsString(InboundOrderCreator.createInvalidSectionCategoryInboundOrderRequest()))
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(Objects.requireNonNull(result.getResolvedException()).getMessage()
						.contains("Product category is invalid to Warehouse Section Category.")));
	}

	@Test
	void shouldReturnBadRequestExceptionIfBadSectionCapability() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/inboundorder/")
				.content(objectMapper.writeValueAsString(InboundOrderCreator.createInvalidSectionCapabilityInboundOrderRequest()))
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof WarehouseSectionCapabilityException));
	}

	@Test
	void shouldReturnListOfCreatedBatches() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/inboundorder/")
				.content(objectMapper.writeValueAsString(InboundOrderCreator.createValidInboundOrderCreateRequest()))
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.inboundOrderId").isNotEmpty())
				.andExpect(jsonPath("$.batchStock[0].batchId").isNotEmpty())
				.andExpect(jsonPath("$.batchStock[1].batchNumber").isNotEmpty())
				.andExpect(jsonPath("$.batchStock[1].productId").isNotEmpty())
				.andExpect(jsonPath("$.batchStock[1].currentTemperature").isNotEmpty())
				.andExpect(jsonPath("$.batchStock[1].minimumTemperature").isNotEmpty())
				.andExpect(jsonPath("$.batchStock[1].quantity").isNotEmpty())
				.andExpect(jsonPath("$.batchStock[1].manufacturingDate").isNotEmpty())
				.andExpect(jsonPath("$.batchStock[1].manufacturingTime").isNotEmpty())
				.andExpect(jsonPath("$.batchStock[1].dueDate").isNotEmpty());
	}

	@Test
	void shouldReturnListOfUpdatedBatches() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/inboundorder/")
				.content(objectMapper.writeValueAsString(InboundOrderCreator.createValidInboundOrderUpdateRequest()))
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.batchStock[0].quantity").value(13));
	}

	@Test
	void shouldReturnBadRequestExceptionBatchWithPurchaseOrder() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/inboundorder/")
				.content(objectMapper.writeValueAsString(InboundOrderCreator
						.createInvalidInboundOrderUpdateRequestBatchWithPurchaseOrder()))
				.header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.ALL))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException));

	}

}
