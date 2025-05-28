package com.hexploretech.library_api.controller.common;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hexploretech.library_api.controller.dto.ErrorDTO;
import com.hexploretech.library_api.controller.dto.ErrorFieldDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ErrorDTO handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		List<FieldError> fieldErrors = ex.getFieldErrors();
		List<ErrorFieldDTO> errorList = fieldErrors
				.stream()
				.map(fe -> new ErrorFieldDTO(fe.getField(), fe.getDefaultMessage()))
				.collect(Collectors.toList());
		return new ErrorDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error", errorList);
	}
}
