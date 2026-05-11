package com.hexploretech.library_api.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "Library API",
		version = "v1",
		contact = @Contact(
			name = "Hexplore Tech",
			email = "contact@hexploretech.com",
			url = "https://www.hexploretech.com"
		),
		description = "API for managing library resources, including books, authors, and users. This API allows clients to perform CRUD operations on library data, manage user accounts, and access library services."
	),
		security = { @SecurityRequirement(name = "bearerAuth") }
)
@SecurityScheme(
	name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer",
		in = SecuritySchemeIn.HEADER
)

public class OpenApiConfiguration {
}
