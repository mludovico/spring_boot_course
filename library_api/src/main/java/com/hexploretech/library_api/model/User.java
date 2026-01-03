package com.hexploretech.library_api.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column
	private String name;

	@Column
	private String email;

	@Column
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Type(ListArrayType.class)
	@Column(name = "roles", columnDefinition = "varchar[]")
	private List<String> roles;

}
