package com.openclassrooms.RentalProject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuration for Swagger.
 */
@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
				.info(new Info().title("Rental app API").description("API which handles rentals").version("1.0"));
	}

	private SecurityScheme createAPIKeyScheme() {
		return new SecurityScheme().name("Bearer Authentication").type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
	}

}