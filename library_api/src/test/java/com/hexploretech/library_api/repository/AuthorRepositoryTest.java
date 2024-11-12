package com.hexploretech.library_api.repository;

import com.hexploretech.library_api.model.Author;
import com.hexploretech.library_api.model.Book;
import com.hexploretech.library_api.model.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    public void saveTest() {
        Author author = new Author();
        author.setName("J. R. R. Tolkien");
        author.setBirthDate(LocalDate.of(1892, 1, 3));
        author.setNationality("British");

        var savedAuthor = authorRepository.save(author);
    }

    public void updateTest() {
        Author author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("J. R. R. Tolkien");
        author.setBirthDate(LocalDate.of(1892, 1, 3));
        author.setNationality("British");

        var updatedAuthor = authorRepository.save(author);
    }

    @Test
    void getAuthorsTest() {
        var authors = authorRepository.findAll();
        System.out.println(authors);
    }

    @Test
    void saveWithBooks() {
        Author author = new Author();
        author.setName("J. K. Rowling");
        author.setBirthDate(LocalDate.of(1965, 7, 31));
        author.setNationality("British");

        final Book book1 = new Book();
        book1.setTitle("Harry Potter and the Philosopher's Stone");
        book1.setIsbn("978-3-16-148410-0");
        book1.setGenre(
                BookGenre.FANTASY);
        book1.setPublicationDate(LocalDate.of(1997, 6, 26));
        book1.setPrice(14.99);
        book1.setAuthor(author);

        final Book book2 = new Book();
        book2.setTitle("Harry Potter and the Chamber of Secrets");
        book2.setIsbn("978-3-16-148410-0");
        book2.setGenre(BookGenre.FANTASY);
        book2.setPublicationDate(LocalDate.of(1998, 7, 2));
        book2.setPrice(14.99);
        book2.setAuthor(author);

        author.setBooks(new ArrayList<>());
        author.getBooks().add(book1);
        author.getBooks().add(book2);

        var savedAuthor = authorRepository.save(author);
        System.out.println(savedAuthor);
    }

    @Test
    void getAuthorBooksById() {
        var author = authorRepository.findById(UUID.fromString("a81a9aef-6f1c-4309-973e-03590621405b")).orElse(null);
        final List<Book> books = author.getBooks();
        System.out.println(author);
    }
}
