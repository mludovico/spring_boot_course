package com.hexploretech.spring_architecture.todos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findByTitle(String title);
    List<TodoEntity> findByDescription(String description);
    List<TodoEntity> findByCompleted(Boolean completed);
}
