package com.hexploretech.library_api.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import com.hexploretech.library_api.model.BookGenre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record BookRegistrationDTO(
		@ISBN
		@NotBlank(message = "ISBN cannot be blank")
		String isbn,
		@NotBlank(message = "Title cannot be blank")
		String title,
		@NotNull(message = "Publication date cannot be blank")
		@Past(message = "Publication date must be in the past")
		LocalDate publicationDate,
		BookGenre genre,
		BigDecimal price,
		UUID authorID
		) {
}
