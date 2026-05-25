package com.github.tkrsatyam.todoservice.service;

import com.github.tkrsatyam.todoservice.dto.TodoRequestDTO;
import com.github.tkrsatyam.todoservice.dto.TodoResponseDTO;
import com.github.tkrsatyam.todoservice.exception.TodoNotFoundException;
import com.github.tkrsatyam.todoservice.model.Todo;
import com.github.tkrsatyam.todoservice.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoResponseDTO> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    public TodoResponseDTO getTodoById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        return mapToResponseDTO(todo);
    }

    @Transactional
    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO) {
        Todo todo = Todo.builder()
                .title(todoRequestDTO.getTitle())
                .description(todoRequestDTO.getDescription())
                .completed(false)
                .build();
        Todo saved = todoRepository.save(todo);
        return mapToResponseDTO(saved);
    }

    @Transactional
    public TodoResponseDTO updateTodo(Long id, TodoRequestDTO todoRequestDTO) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        todo.setTitle(todoRequestDTO.getTitle());
        todo.setDescription(todoRequestDTO.getDescription());
        Todo updated = todoRepository.save(todo);
        return mapToResponseDTO(updated);
    }

    @Transactional
    public TodoResponseDTO toggleComplete(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));

        todo.setCompleted(!todo.isCompleted());
        Todo updated = todoRepository.save(todo);
        return mapToResponseDTO(updated);
    }

    @Transactional
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException(id);
        }
        todoRepository.deleteById(id);
    }

    private TodoResponseDTO mapToResponseDTO(Todo todo) {
        return TodoResponseDTO.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .createdAt(todo.getCreatedAt())
                .build();
    }
}
