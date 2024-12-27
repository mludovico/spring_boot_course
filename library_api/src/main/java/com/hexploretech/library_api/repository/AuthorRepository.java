package com.hexploretech.library_api.repository;

import com.hexploretech.library_api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    List<Author> findByNameContainingIgnoreCaseAndNationalityContainingIgnoreCaseAndBirthDate(String name,
                                                                                              String nationality,
                                                                                              LocalDate birthDate);
    List<Author> findByNameContainingIgnoreCaseAndNationalityContainingIgnoreCase(String name, String string);

    List<Author> findByNameContainingIgnoreCaseAndBirthDate(String name, LocalDate birthDate);

    List<Author> findByNationalityContainingIgnoreCaseAndBirthDate(String name, LocalDate birthDate);

    List<Author> findByNameContainingIgnoreCase(String name);

    List<Author> findByNationalityContainingIgnoreCase(String nationality);

    List<Author> findByBirthDate(LocalDate birthDate);

    Optional<Author> findByNameAndBirthDateAndNationality(String name, LocalDate birthDate, String nationality);
}
