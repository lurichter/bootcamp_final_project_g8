package com.mercadolibre.bootcamp_gabriel_duarte.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;

public abstract class ControllerTest extends IntegrationTest {
	@Autowired
	protected TestRestTemplate testRestTemplate;

	protected <T> RequestEntity<T> getDefaultRequestEntity() {
		HttpHeaders headers = new HttpHeaders();
		return new RequestEntity<>(headers, HttpMethod.GET, null);
	}
}
