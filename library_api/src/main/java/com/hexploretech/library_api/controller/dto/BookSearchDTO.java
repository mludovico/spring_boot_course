package com.hexploretech.library_api.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.hexploretech.library_api.model.BookGenre;

public record BookSearchDTO(
		UUID id,
		String isbn,
		String title,
		LocalDate publicationDate,
		BookGenre genre,
		BigDecimal price,
		AuthorDTO author
) {
}
