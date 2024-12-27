package com.hexploretech.library_api.controller.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ErrorDTO(int status, String message, List<ErrorFieldDTO> errors) {
	public static ErrorDTO badRequest(String message) {
		return new ErrorDTO(HttpStatus.BAD_REQUEST.value(), message, List.of());
	}
}
