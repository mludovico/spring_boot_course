package com.hexploretech.library_api.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(UUID id, @NotBlank(message = "Name cannot be blank") String name,
		@Email(message = "Invalid email") @NotBlank(message = "Email cannot be blank") String email,
		@NotBlank(message = "Password cannot be blank") String password, String[] roles, LocalDateTime createdAt,

		LocalDateTime updatedAt) {
}
