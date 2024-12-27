package com.hexploretech.library_api.validators;

import org.springframework.stereotype.Component;

import com.hexploretech.library_api.exceptions.DuplicateRegistryException;
import com.hexploretech.library_api.model.Author;
import com.hexploretech.library_api.repository.AuthorRepository;

@Component
public class AuthorValidator {
	private AuthorRepository authorRepository;

	public AuthorValidator(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	public void validateAuthor(Author author) throws DuplicateRegistryException {
		if (authorExists(author)) {
			throw new DuplicateRegistryException("Author already exists");
		}
	}

	private boolean authorExists(Author author) {
		return authorRepository.findByNameAndBirthDateAndNationality(author.getName(), author.getBirthDate(),
				author.getNationality()).isPresent();
	}
}
