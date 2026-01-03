package com.hexploretech.library_api.controller.mappers;

import org.mapstruct.Mapper;

import com.hexploretech.library_api.controller.dto.BookDTO;
import com.hexploretech.library_api.controller.dto.BookRegistrationDTO;
import com.hexploretech.library_api.model.Book;

@Mapper(componentModel = "spring")
public abstract class BookMapper {
	public abstract Book toEntity(BookRegistrationDTO bookDTO);

	public abstract BookDTO toDto(Book book);
}
