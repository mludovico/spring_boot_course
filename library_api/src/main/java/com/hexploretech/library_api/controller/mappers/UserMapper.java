package com.hexploretech.library_api.controller.mappers;

import org.mapstruct.Mapper;

import com.hexploretech.library_api.controller.dto.UserDTO;
import com.hexploretech.library_api.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toEntity(UserDTO userDTO);

	UserDTO toDTO(User user);
}
