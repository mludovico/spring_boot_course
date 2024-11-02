package com.hexploretech.spring_architecture.todos;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final TodoValidator todoValidator;

    public TodoService(TodoRepository todoRepository, TodoValidator todoValidator) {
        this.todoRepository = todoRepository;
        this.todoValidator = todoValidator;
    }

    public TodoEntity create(TodoEntity todo) {
        todoValidator.validate(todo);
        return todoRepository.save(todo);
    }

    public TodoEntity update(Integer id, TodoEntity todo) {
        todo.setId(id);
        return todoRepository.save(todo);
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    public TodoEntity findById(Long id) {
        return todoRepository.findById(id).orElse(null);
    }

    public List<TodoEntity> findAll() {
        return todoRepository.findAll();
    }

    public List<TodoEntity> findByTitle(String title) {
        return todoRepository.findByTitle(title);
    }

    public List<TodoEntity> findByDescription(String description) {
        return todoRepository.findByDescription(description);
    }

    public List<TodoEntity> findByCompleted(Boolean completed) {
        return todoRepository.findByCompleted(completed);
    }

    public TodoEntity toggleCompleted(Long id) {
        TodoEntity todo = todoRepository.findById(id).orElse(null);
        if (todo != null) {
            todo.setCompleted(!todo.getCompleted());
            todoRepository.save(todo);
        }
        return todo;
    }
}
