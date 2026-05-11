package com.hexploretech.library_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexploretech.library_api.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
	User findUserByEmail(String email);

	User findUserByName(String name);
}
