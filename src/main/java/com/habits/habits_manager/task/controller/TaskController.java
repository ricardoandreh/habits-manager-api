package com.habits.habits_manager.task.controller;

import com.habits.habits_manager.task.dtos.TaskRequestDTO;
import com.habits.habits_manager.task.dtos.TaskResponseDTO;
import com.habits.habits_manager.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.habits.habits_manager.task.dtos.TaskUpdateDTO;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    final TaskService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO insert(@Valid @RequestBody TaskRequestDTO obj) {
        return service.insert(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO update(@PathVariable("id") Long id, @RequestBody TaskUpdateDTO obj) {
        return service.update(id, obj);
    }
}
