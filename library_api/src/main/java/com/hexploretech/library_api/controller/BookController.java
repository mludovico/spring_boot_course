package com.hexploretech.library_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexploretech.library_api.controller.dto.BookDTO;
import com.hexploretech.library_api.controller.dto.BookRegistrationDTO;
import com.hexploretech.library_api.controller.dto.ErrorDTO;
import com.hexploretech.library_api.controller.mappers.BookMapper;
import com.hexploretech.library_api.model.Author;
import com.hexploretech.library_api.model.Book;
import com.hexploretech.library_api.service.AuthorService;
import com.hexploretech.library_api.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;
	private final BookMapper bookMapper;
	private final AuthorService authorService;

	@PostMapping
	public ResponseEntity<Object> createBook(@RequestBody @Valid BookRegistrationDTO book) {
		try {
			Author author = authorService.getAuthorById(book.authorID()).orElseThrow();
			Book bookEntity = bookMapper.toEntity(book);
			bookEntity.setAuthor(author);
			bookService.createBook(bookEntity);
			return ResponseEntity.ok("Book created successfully");
		} catch (Exception e) {
			ErrorDTO error = ErrorDTO.badRequest("Failed to create book");
			return ResponseEntity.status(error.status()).body(error.errors());
		}
	}

	@GetMapping
	public ResponseEntity<List<BookDTO>> getAllBooks() {
		List<BookDTO> books = bookService.getBooks().stream().map(bookMapper::toDto).toList();
		return ResponseEntity.ok(books);
	}
}
