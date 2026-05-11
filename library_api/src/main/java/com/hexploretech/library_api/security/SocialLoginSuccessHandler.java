package com.hexploretech.library_api.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.hexploretech.library_api.model.User;
import com.hexploretech.library_api.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SocialLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static final String DEFAULT_PASSWORD = "password";

	private final UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		OAuth2User oAuth2User = token.getPrincipal();
		String email = oAuth2User.getAttribute("email");
		User user = userService.findByEmail(email);

		if (user == null) {
			String name = oAuth2User.getAttribute("name");
			user = registerUser(email, name);
		}

		CustomAuthentication customAuthentication = new CustomAuthentication(user);
		customAuthentication.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(customAuthentication);
		super.onAuthenticationSuccess(request, response, customAuthentication);
	}

	private User registerUser(String email, String name) {
		User user;
		user = new User();
		user.setEmail(email);
		user.setName(name);
		user.setPassword(DEFAULT_PASSWORD);
		user.setRoles(List.of("USER"));
		userService.createUser(user);
		return user;
	}
}
