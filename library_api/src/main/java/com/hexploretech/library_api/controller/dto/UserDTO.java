package com.hexploretech.library_api.controller.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(UUID id, String name, String email, String password, String[] roles, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
}
