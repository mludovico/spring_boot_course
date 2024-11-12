package com.hexploretech.library_api.repository;

import com.hexploretech.library_api.model.Author;
import com.hexploretech.library_api.model.Book;
import com.hexploretech.library_api.model.BookGenre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void saveTest() {
        final Book book = new Book();
        book.setTitle("The Lord of the Rings");
        book.setIsbn("978-3-16-148410-0");
        book.setGenre(BookGenre.FANTASY);
        book.setPublicationDate(LocalDate.of(1954, 7, 29));
        book.setPrice(19.99);

        Author author = authorRepository.findById(UUID.fromString("c3d0a749-7dbb-4d51-b5bf-fb43e0b01405")).orElse(null);
        book.setAuthor(author);

        var savedBook = bookRepository.save(book);
        System.out.println(savedBook);
    }

    @Test
    void saveCascade() {
        final Book book = new Book();
        book.setTitle("Harry Potter and the Philosopher's Stone");
        book.setIsbn("978-3-16-148410-0");
        book.setGenre(BookGenre.FANTASY);
        book.setPublicationDate(LocalDate.of(1997, 6, 26));
        book.setPrice(14.99);

        final Author author = new Author();
        author.setName("J. K. Rowling");
        author.setBirthDate(LocalDate.of(1965, 7, 31));
        author.setNationality("British");

        book.setAuthor(author);

        var savedBook = bookRepository.save(book);
        System.out.println(savedBook);
    }

    @Test
    void createAuthorAndSabeBook() {
        final Author author = new Author();
        author.setName("G. R. R. Martin");
        author.setBirthDate(LocalDate.of(1948, 9, 20));
        author.setNationality("American");

        final Author savedAuthor = authorRepository.save(author);

        final Book book = new Book();
        book.setTitle("A Game of Thrones");
        book.setIsbn("978-3-16-148410-0");
        book.setGenre(BookGenre.FANTASY);
        book.setPublicationDate(LocalDate.of(1996, 8, 6));
        book.setPrice(24.99);
        book.setAuthor(savedAuthor);

        var savedBook = bookRepository.save(book);
        System.out.println(savedBook);
    }

    @Test
    void getBooksTest() {
        var books = bookRepository.findAll();
        System.out.println(books);
    }

    @Test
    void getBookByAuthor() {
        final Author author = authorRepository.findById(UUID.fromString("a81a9aef-6f1c-4309-973e-03590621405b")).orElse(null);
        final List<Book> books = bookRepository.findByAuthor(author);
        System.out.println(books);
    }

    @Test
    void getBookByTitle() {
        final List<Book> books = bookRepository.findByTitleContainingIgnoreCase("Potter");
        System.out.println(books);
    }

    @Test
    void customQuery() {
        final List<Book> books = bookRepository.getBookUsingCustomQuery(
                UUID.fromString("94057bb1-3bd3-4112-a911-b031327b01ac"),
                UUID.fromString("a81a9aef-6f1c-4309-973e-03590621405b"));
        System.out.println(books);
    }

    @Test
    void deleteByAuthor() {
        final Author author = authorRepository.findById(UUID.fromString("aefeeda6-a2af-4442-bc70-983c6548379c")).orElse(null);
        bookRepository.deleteByAuthor(author);
    }

    @Test
    void updateBookByAuthor() {
        final Author author = authorRepository.findById(UUID.fromString("6a07b322-0133-477c-be55-0e8251487c86")).orElse(null);
        final List<Book> books = bookRepository.findByAuthor(author);
        final Book book = books.getFirst();
        book.setPrice(29.99);
        bookRepository.updateBookByAuthor(book, author);
    }
}