package com.hexploretech.library_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Data
@ToString(exclude = "author")
public class Book {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20, nullable = false)
    private String isbn;

    @Column(length = 150, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate publicationDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private BookGenre genre;

    @Column(precision = 12)
    private double price;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDate createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDate updatedAt;

    @JoinColumn(name = "id_author")
    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;
}
