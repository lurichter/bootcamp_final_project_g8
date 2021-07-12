package com.mercadolibre.group8_bootcamp_finalproject.integration;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.LoginRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.AccountResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.UnauthorizedException;
import com.mercadolibre.group8_bootcamp_finalproject.util.LoginUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SessionControllerIT extends ControllerTest {

	@Test
	void shouldLoginAsOperator() {
		ResponseEntity<AccountResponseDTO> responseEntity = LoginUtil.loginAsOperator(this.testRestTemplate);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(responseEntity.getBody());
		assertNotNull(responseEntity.getBody().getUsername());
		assertNotNull(responseEntity.getBody().getToken());
	}

	@Test
	void shouldUnauthorizedLogin() {
		ResponseEntity responseEntity = LoginUtil.login(this.testRestTemplate,
				new LoginRequestDTO("test", "test"));

		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}
}
