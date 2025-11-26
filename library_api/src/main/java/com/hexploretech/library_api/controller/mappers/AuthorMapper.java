package com.hexploretech.library_api.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hexploretech.library_api.controller.dto.AuthorDTO;
import com.hexploretech.library_api.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
	@Mapping(source = "name", target = "name")
	@Mapping(source = "nationality", target = "nationality")
	@Mapping(source = "birthDate", target = "birthDate")
	Author toEntity(AuthorDTO authorDTO);
	AuthorDTO toDTO(Author author);
}
