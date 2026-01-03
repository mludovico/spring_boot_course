package com.hexploretech.library_api.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record AuthorDTO(UUID uuid,
		@NotBlank(message = "Name is required")
		@Size(min = 2, max = 100, message = "Name must be at least 2 and most 100 characters")
		String name,

		@NotNull(message = "Birth date is required")
		@Past(message = "Birth date must be in the past")
		LocalDate birthDate,

		@NotBlank(message = "Nationality is required")
		@Size(min = 2, max = 50, message = "Nationality must be at least 2 and most 50 characters")
		String nationality) {
}
