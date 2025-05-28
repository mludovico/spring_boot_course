package com.hexploretech.library_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexploretech.library_api.controller.dto.BookRegistrationDTO;
import com.hexploretech.library_api.controller.dto.ErrorDTO;
import com.hexploretech.library_api.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;

	@PostMapping
	public ResponseEntity<Object> createBook(@RequestBody @Valid BookRegistrationDTO book) {
		try {
			// Logic to create a book
			return ResponseEntity.ok("Book created successfully");
		} catch (Exception e) {
			ErrorDTO error = ErrorDTO.badRequest("Failed to create book");
			return ResponseEntity.status(error.status()).body(error.errors());
		}
	}
}
