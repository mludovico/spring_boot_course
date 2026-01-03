package com.hexploretech.library_api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hexploretech.library_api.model.User;
import com.hexploretech.library_api.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return org.springframework.security.core.userdetails.User.builder()
				.username(user.getName())
				.password(user.getPassword())
				.roles(user.getRoles().toArray(new String[0]))
				.build();
	}
}
