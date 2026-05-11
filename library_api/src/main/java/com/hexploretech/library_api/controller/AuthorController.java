package com.hexploretech.library_api.controller;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hexploretech.library_api.controller.dto.AuthorDTO;
import com.hexploretech.library_api.controller.dto.ErrorDTO;
import com.hexploretech.library_api.controller.mappers.AuthorMapper;
import com.hexploretech.library_api.exceptions.DuplicateRegistryException;
import com.hexploretech.library_api.exceptions.OperationNotPermittedException;
import com.hexploretech.library_api.model.Author;
import com.hexploretech.library_api.service.AuthorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/authors")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Endpoints for managing authors in the library system")
public class AuthorController {
	private final AuthorService authorService;
	private final AuthorMapper authorMapper;

	@PostMapping
	@Operation(summary = "Create a new author", description = "Adds a new author to the library system. Requires ADMIN role.")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Author created successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
			@ApiResponse(responseCode = "422", description = "Unprocessable Entity - Validation errors"),
			@ApiResponse(responseCode = "409", description = "Conflict - Duplicate registry")
	})
	public ResponseEntity<Object> saveAuthor(@RequestBody @Valid AuthorDTO author) {
		Author authorEntity = authorMapper.toEntity(author);
		try {
			authorService.save(authorEntity);
		} catch (DuplicateRegistryException e) {
			var error = ErrorDTO.badRequest(e.getMessage());
			return ResponseEntity.status(error.status()).body(error.errors());
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(authorEntity.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@Operation(summary = "Get all authors", description = "Retrieves a list of all authors in the library system. Accessible by ADMIN and USER roles.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Authors retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
	})
	public ResponseEntity<List<Author>> getAuthors(Authentication authentication) {
		System.out.println("Authentication: " + authentication);
		log.trace("trace log");
		log.debug("debug log");
		log.info("info log");
		log.warn("warn log");
		log.error("error log");
		return ResponseEntity.ok(authorService.getAuthors());
	}

	@GetMapping("/{authorId}")
	@Operation(summary = "Get author by ID", description = "Retrieves an author by their unique ID. Accessible by ADMIN and USER roles.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Author retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
			@ApiResponse(responseCode = "404", description = "Author not found")
	})
	public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable String authorId) {
		UUID authorUUID = UUID.fromString(authorId);
		return authorService.getAuthorById(authorUUID)
				.map(authorEntity -> ResponseEntity.ok(authorMapper.toDTO(authorEntity)))
				.orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{authorId}")
	@Operation(summary = "Delete an author", description = "Deletes an author by their unique ID. Requires ADMIN role.")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Author deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Operation not permitted"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
			@ApiResponse(responseCode = "404", description = "Author not found"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
	public ResponseEntity<Object> deleteAuthor(@PathVariable String authorId) {
		try {
			authorService.delete(authorId);
			return ResponseEntity.noContent().build();
		} catch (OperationNotPermittedException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/search")
	@Operation(summary = "Search authors", description = "Search for authors by name")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Authors retrieved successfully"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions")
	})
	public ResponseEntity<List<AuthorDTO>> searchAuthors(@RequestParam(required = false) String name,
			@RequestParam(required = false) String nationality, @RequestParam(required = false) LocalDate birthDate) {
		List<Author> authors = authorService.searchAuthorsByExample(name, nationality, birthDate);
		return ResponseEntity.ok(authors.stream().map(Author::toDTO).toList());
	}

	@PutMapping("/{authorId}")
	@Operation(summary = "Update an author", description = "Updates an existing author by their unique ID. Requires ADMIN role.")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Author updated successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Validation errors"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required"),
			@ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
			@ApiResponse(responseCode = "404", description = "Author not found")
	})
	public ResponseEntity<Object> updateAuthor(@PathVariable String authorId, @RequestBody @Valid AuthorDTO author) {
		UUID authorUUID = UUID.fromString(authorId);
		Author authorEntity = authorMapper.toEntity(author);
		authorEntity.setId(authorUUID);
		try {
			authorService.save(authorEntity);
		} catch (DuplicateRegistryException e) {
			var error = ErrorDTO.badRequest(e.getMessage());
			return ResponseEntity.status(error.status()).body(error.errors());
		}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(authorEntity.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
}
