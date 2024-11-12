package com.hexploretech.library_api.repository;

import com.hexploretech.library_api.model.Author;
import com.hexploretech.library_api.model.Book;
import com.hexploretech.library_api.model.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByAuthor(Author author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByGenre(BookGenre genre);

    List<Book> findByPriceBetween(double min, double max);

    @Query("SELECT b FROM Book b WHERE b.id = :bookId OR b.author.id = :authorId")
    List<Book> getBookUsingCustomQuery(UUID bookId, UUID authorId);

    @Transactional
    void deleteByAuthor(Author author);

    @Transactional
    @Modifying
    @Query("UPDATE Book b SET b.title = :#{#book.title}, b.isbn = :#{#book.isbn}, b.genre = :#{#book.genre}, b.publicationDate = :#{#book.publicationDate}, b.price = :#{#book.price} WHERE b.author = :author")
    void updateBookByAuthor(Book book, Author author);
}
