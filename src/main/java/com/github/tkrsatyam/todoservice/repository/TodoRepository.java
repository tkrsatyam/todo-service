package com.github.tkrsatyam.todoservice.repository;

import com.github.tkrsatyam.todoservice.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCompletedFalse();
    List<Todo> findByCompletedTrue();
}
