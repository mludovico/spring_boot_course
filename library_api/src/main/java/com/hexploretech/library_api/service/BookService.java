package com.hexploretech.library_api.service;

import org.springframework.stereotype.Service;

import com.hexploretech.library_api.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	private final BookRepository bookRepository;
}
