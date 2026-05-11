package com.hexploretech.library_api.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hexploretech.library_api.model.User;
import com.hexploretech.library_api.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private final UserService userService;
	private final PasswordEncoder encoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		User user = userService.findByEmail(username);
		if (user != null) {
			String encryptedPassword = user.getPassword();
			boolean passwordMatch = encoder.matches(password, encryptedPassword);
			if (passwordMatch) {
				return new CustomAuthentication(user);
			}
		}
		throw new AuthenticationException("User and/or password incorrect") {
		};
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
	}
}
