package com.mercadolibre.group8_bootcamp_finalproject.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static com.mercadolibre.group8_bootcamp_finalproject.util.ScopeUtils.SCOPE_VALUE;
import static com.mercadolibre.group8_bootcamp_finalproject.util.ScopeUtils.isLocalScope;
import static java.lang.String.format;

@Configuration
public class SpringDocConfig {

	@Value("${server.port}")
	private int serverPort;

	@Bean
	public OpenAPI customOpenAPI(@Value("${app.title}") String appName, @Value("${app.description}") String description, @Value("${app.version}") String version) {
		final String securitySchemeName = "bearerAuth";
		OpenAPI api = new OpenAPI()
				.info(new Info().title(appName)
				.version(version)
				.description(description)
				.contact(new Contact().name("group8-bootcamp-finalproject")
						.email("stefanie.vicente@mercadolivre.com")))
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.components(
						new Components()
								.addSecuritySchemes(securitySchemeName,
										new SecurityScheme()
												.name(securitySchemeName)
												.type(SecurityScheme.Type.HTTP)
												.scheme("bearer")
												.in(SecurityScheme.In.HEADER)
												.bearerFormat("JWT")
								)
				);

		api.addServersItem(new Server().url(isLocalScope() ? "http://localhost:"+serverPort : format("https://%s_%s.furyapps.io", SCOPE_VALUE, appName))
				.description(format("Scope %s", SCOPE_VALUE)));
		return api;
	}

}