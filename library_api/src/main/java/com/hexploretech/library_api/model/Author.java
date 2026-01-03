package com.hexploretech.library_api.model;

import com.hexploretech.library_api.controller.dto.AuthorDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
@EntityListeners(AuditingEntityListener.class)
public class Author {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(length = 50, nullable = false)
    private String nationality;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

//    @Transient
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Book> books;

    public AuthorDTO toDTO() {
        return new AuthorDTO(this.id, this.name, this.birthDate, this.nationality);
    }
}
