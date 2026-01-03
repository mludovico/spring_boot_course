package com.hexploretech.library_api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.hexploretech.library_api.exceptions.OperationNotPermittedException;
import com.hexploretech.library_api.model.Author;
import com.hexploretech.library_api.repository.AuthorRepository;
import com.hexploretech.library_api.repository.BookRepository;
import com.hexploretech.library_api.validators.AuthorValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
	private final AuthorRepository authorRepository;
	private final AuthorValidator authorValidator;
	private final BookRepository bookRepository;
	private final SecurityService securityService;

	public void save(Author author) {
		author.setUser(securityService.getLoggedUser());
		authorValidator.validateAuthor(author);
		authorRepository.save(author);
	}

	public List<Author> getAuthors() {
		return authorRepository.findAll();
	}

	public Optional<Author> getAuthorById(UUID authorId) {
		return authorRepository.findById(authorId);
	}

	public void delete(String authorIdString) {
		if (hasBooks(UUID.fromString(authorIdString))) {
			throw new OperationNotPermittedException("Author has books");
		}
		UUID authorId = UUID.fromString(authorIdString);
		authorRepository.deleteById(authorId);
	}

	public List<Author> searchAuthors(String name, String nationality, LocalDate birthDate) {
		int bitmap = (name != null ? 0b100 : 0b000) + (nationality != null ? 0b010 : 0b000) + (birthDate != null
				? 0b001
				: 0b000);
		switch (bitmap) {
		case 0b001:
			return authorRepository.findByBirthDate(birthDate);
		case 0b010:
			return authorRepository.findByNationalityContainingIgnoreCase(nationality);
		case 0b011:
			return authorRepository.findByNationalityContainingIgnoreCaseAndBirthDate(nationality, birthDate);
		case 0b100:
			return authorRepository.findByNameContainingIgnoreCase(name);
		case 0b101:
			return authorRepository.findByNameContainingIgnoreCaseAndBirthDate(name, birthDate);
		case 0b110:
			return authorRepository.findByNameContainingIgnoreCaseAndNationalityContainingIgnoreCase(name, nationality);
		case 0b111:
			return authorRepository.findByNameContainingIgnoreCaseAndNationalityContainingIgnoreCaseAndBirthDate(name,
					nationality, birthDate);
		default:
			return authorRepository.findAll();
		}
	}

	public List<Author> searchAuthorsByExample(String name, String nationality, LocalDate birthDate) {
		Author author = new Author();
		author.setName(name);
		author.setNationality(nationality);
		author.setBirthDate(birthDate);

		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreNullValues()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example<Author> authorExample = Example.of(author, matcher);
		return authorRepository.findAll(authorExample);
	}

	public boolean hasBooks(UUID authorId) {
		return bookRepository.existsByAuthorId(authorId);
	}
}
