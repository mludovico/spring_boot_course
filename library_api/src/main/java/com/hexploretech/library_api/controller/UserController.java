package com.hexploretech.library_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hexploretech.library_api.controller.dto.UserDTO;
import com.hexploretech.library_api.controller.mappers.UserMapper;
import com.hexploretech.library_api.model.User;
import com.hexploretech.library_api.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final UserMapper userMapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User save(@RequestBody UserDTO userDTO) {
		return userService.createUser(userMapper.toEntity(userDTO));
	}
}
