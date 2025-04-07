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

    final TaskService taskService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponseDTO> findAll() {

        return this.taskService.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO findById(@PathVariable Long id) {

        return this.taskService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDTO insert(@RequestBody @Valid TaskRequestDTO obj) {

        return this.taskService.insert(obj);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        this.taskService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO update(@PathVariable("id") Long id, @RequestBody TaskUpdateDTO obj) {

        return this.taskService.update(id, obj);
    }
}
