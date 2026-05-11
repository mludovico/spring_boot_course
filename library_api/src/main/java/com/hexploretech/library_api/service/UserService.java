package com.hexploretech.library_api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexploretech.library_api.model.User;
import com.hexploretech.library_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public User createUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public User findByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

	public User findByUsername(String username) {
		return userRepository.findUserByName(username);
	}
}
