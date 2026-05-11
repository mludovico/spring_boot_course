package com.hexploretech.library_api.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.hexploretech.library_api.model.User;
import com.hexploretech.library_api.security.CustomAuthentication;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityService {

	public User getLoggedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof CustomAuthentication customAuth) {
			return customAuth.getUser();
		}
		return null;
	}
}
