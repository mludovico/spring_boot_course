package com.hexploretech.library_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hexploretech.library_api.model.Book;
import com.hexploretech.library_api.model.User;
import com.hexploretech.library_api.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	private final BookRepository bookRepository;
	private final SecurityService securityService;

	public Book createBook(Book book) {
		User user = securityService.getLoggedUser();
		book.setUser(user);
		return bookRepository.save(book);
	}

	public List<Book> getBooks() {
		return bookRepository.findAll();
	}
}
