package com.hexploretech.library_api.controller;

import com.hexploretech.library_api.controller.dto.AuthorDTO;
import com.hexploretech.library_api.controller.dto.ErrorDTO;
import com.hexploretech.library_api.controller.mappers.AuthorMapper;
import com.hexploretech.library_api.exceptions.DuplicateRegistryException;
import com.hexploretech.library_api.exceptions.OperationNotPermittedException;
import com.hexploretech.library_api.model.Author;
import com.hexploretech.library_api.service.AuthorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {
    private AuthorService authorService;
	private AuthorMapper authorMapper;

    @PostMapping
    public ResponseEntity<Object> saveAuthor(@RequestBody @Valid AuthorDTO author) {
        Author authorEntity = authorMapper.toEntity(author);
		try {
			authorService.save(authorEntity);
		} catch (DuplicateRegistryException e) {
			var error = ErrorDTO.badRequest(e.getMessage());
            return ResponseEntity.status(error.status()).body(error.errors());
		}
		URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorEntity.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAuthors() {
        return ResponseEntity.ok(authorService.getAuthors());
    }

    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable String authorId) {
        UUID authorUUID = UUID.fromString(authorId);
        Optional<Author> author = authorService.getAuthorById(authorUUID);
		return authorService.getAuthorById(authorUUID).map(
				authorEntity -> ResponseEntity.ok(authorMapper.toDTO(authorEntity))
		).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{authorId}")
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
    public ResponseEntity<List<AuthorDTO>> searchAuthors(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) String nationality,
                                                        @RequestParam(required = false) LocalDate birthDate) {
        List<Author> authors = authorService.searchAuthorsByExample(name, nationality, birthDate);
        return ResponseEntity.ok(authors.stream().map(Author::toDTO).toList());
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<Object> updateAuthor(
			@PathVariable
			String authorId,
			@RequestBody
			@Valid
			AuthorDTO author) {
        UUID authorUUID = UUID.fromString(authorId);
        Author authorEntity = authorMapper.toEntity(author);
        authorEntity.setId(authorUUID);
        try {
            authorService.save(authorEntity);
        } catch (DuplicateRegistryException e) {
            var error = ErrorDTO.badRequest(e.getMessage());
            return ResponseEntity.status(error.status()).body(error.errors());
        }
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorEntity.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
