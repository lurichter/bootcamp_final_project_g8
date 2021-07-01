package com.mercadolibre.group8_bootcamp_finalproject;

import com.mercadolibre.group8_bootcamp_finalproject.config.SpringConfig;
import com.mercadolibre.group8_bootcamp_finalproject.util.ScopeUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		ScopeUtils.calculateScopeSuffix();
		new SpringApplicationBuilder(SpringConfig.class).registerShutdownHook(true)
				.run(args);
	}
}
