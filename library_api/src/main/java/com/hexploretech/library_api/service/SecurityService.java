package com.hexploretech.library_api.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hexploretech.library_api.model.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityService {
	private final UserService userService;

	public User getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userService.findByUsername(userDetails.getUsername());
	}
}
