package com.mercadolibre.bootcamp_gabriel_duarte;

import com.mercadolibre.bootcamp_gabriel_duarte.config.SpringConfig;
import com.mercadolibre.bootcamp_gabriel_duarte.util.ScopeUtils;
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
