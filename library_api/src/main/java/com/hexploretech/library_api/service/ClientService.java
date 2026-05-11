package com.hexploretech.library_api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexploretech.library_api.model.Client;
import com.hexploretech.library_api.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {
	private final ClientRepository clientRepository;
	private final PasswordEncoder passwordEncoder;

	public Client save(Client client) {
		var encodedPassword = passwordEncoder.encode(client.getClientSecret());
		client.setClientSecret(encodedPassword);
		return clientRepository.save(client);
	}

	public Client findByClientId(String clientId) {
		return clientRepository.findByClientId(clientId);
	}
}
