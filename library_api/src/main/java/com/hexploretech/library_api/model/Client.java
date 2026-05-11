package com.hexploretech.library_api.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String clientId;

	private String clientSecret;

	private String redirectUri;

	private String scope;
}
