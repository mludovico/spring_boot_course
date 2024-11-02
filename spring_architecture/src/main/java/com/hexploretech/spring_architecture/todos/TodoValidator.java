package com.hexploretech.spring_architecture.todos;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TodoValidator {
    private TodoRepository todoRepository;

    public TodoValidator(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void validate(TodoEntity todo) {
        if(todo.getTitle() == null || todo.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
        if(todo.getDescription() == null || todo.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
        if(todo.getCompleted() == null) {
            todo.setCompleted(false);
        }

        List<TodoEntity> storedTodo = todoRepository.findByTitle(todo.getTitle());
        if(!storedTodo.isEmpty()) {
            throw new IllegalArgumentException("Title already exists");
        }
    }
}
