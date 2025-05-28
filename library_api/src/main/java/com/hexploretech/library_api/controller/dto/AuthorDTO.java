package com.hexploretech.library_api.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.hexploretech.library_api.model.Author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record AuthorDTO(
        UUID uuid,
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be at least 2 and most 100 characters")
        String name,
        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,
        @NotBlank(message = "Nationality is required")
        @Size(min = 2, max = 50, message = "Nationality must be at least 2 and most 50 characters")
        String nationality) {
    public Author toEntity() {
        Author author = new Author();
        author.setName(this.name);
        author.setBirthDate(this.birthDate);
        author.setNationality(this.nationality);
        return author;
    }

    public Author toEntityMapAuthor(Author author) {
        var fields = this.getClass().getDeclaredFields();

        for (var field : fields) {
            try {
                var value = field.get(this);
                if (value != null) {
                    var methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    var method = Author.class.getMethod(methodName, field.getType());
                    method.invoke(author, value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return author;
    }
}
