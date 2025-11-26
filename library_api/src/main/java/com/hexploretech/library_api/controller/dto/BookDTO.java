package com.hexploretech.library_api.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.hexploretech.library_api.model.BookGenre;

public record BookDTO(UUID id, String isbn, String title, LocalDate publicationDate, BookGenre genre, double price,
		LocalDateTime createdAt, LocalDateTime updatedAt) {
}
