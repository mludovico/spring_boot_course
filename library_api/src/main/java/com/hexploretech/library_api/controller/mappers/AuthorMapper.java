package com.hexploretech.library_api.controller.mappers;

import org.mapstruct.Mapper;

import com.hexploretech.library_api.controller.dto.AuthorDTO;
import com.hexploretech.library_api.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
	Author toEntity(com.hexploretech.library_api.controller.dto.AuthorDTO authorDTO);
	AuthorDTO toDTO(Author author);
}
