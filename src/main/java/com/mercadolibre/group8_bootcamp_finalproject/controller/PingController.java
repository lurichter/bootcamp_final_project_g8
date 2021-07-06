package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.newrelic.api.agent.NewRelic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

	@GetMapping("/ping")
	public String ping() {
		NewRelic.ignoreTransaction();
		return "pong";
	}
}
