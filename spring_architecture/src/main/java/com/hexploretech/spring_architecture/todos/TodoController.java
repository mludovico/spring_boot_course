package com.hexploretech.spring_architecture.todos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public TodoEntity create(@RequestBody TodoEntity todo) {
        try {
            return todoService.create(todo);
        } catch (Exception e) {
            var errorMessage = e.getMessage();
            throw new ResponseStatusException(HttpStatus.CONFLICT, errorMessage);
        }
    }

    @PatchMapping("/{id}")
    public TodoEntity toggleCompleted(@PathVariable Long id) {
        return todoService.toggleCompleted(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        todoService.delete(id);
    }

    @PutMapping("/{id}")
    public TodoEntity update(@RequestBody TodoEntity todo, @PathVariable Long id) {
        return todoService.update(id.intValue(), todo);
    }

    @GetMapping("/{id}")
    public TodoEntity findById(@PathVariable Long id) {
        return todoService.findById(id);
    }

    @GetMapping
    public List<TodoEntity> findAll() {
        return todoService.findAll();
    }

    @GetMapping("/search")
    public List<TodoEntity> findByParam(@RequestParam(required = false) String title,
                                        @RequestParam(required = false) String description,
                                        @RequestParam(required = false) Boolean completed) {
        var list = new HashSet<TodoEntity>();
        list.addAll(todoService.findByTitle(title));
        list.addAll(todoService.findByDescription(description));
        list.addAll(todoService.findByCompleted(completed));
        return List.copyOf(list);
    }
}
