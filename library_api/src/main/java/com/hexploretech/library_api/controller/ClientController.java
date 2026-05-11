package com.hexploretech.library_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.hexploretech.library_api.model.Client;
import com.hexploretech.library_api.service.ClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {
	private final ClientService clientService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void save(@RequestBody Client client) {
		clientService.save(client);
	}
}
