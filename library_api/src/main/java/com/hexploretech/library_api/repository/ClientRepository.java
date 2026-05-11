package com.hexploretech.library_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexploretech.library_api.model.Client;

public interface ClientRepository extends JpaRepository<Client, UUID> {
	Client findByClientId(String clientId);
}
