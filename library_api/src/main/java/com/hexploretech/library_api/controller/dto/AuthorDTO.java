package com.hexploretech.library_api.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.hexploretech.library_api.model.Author;

public record AuthorDTO(UUID uuid, String name, LocalDate birthDate, String nationality) {
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
