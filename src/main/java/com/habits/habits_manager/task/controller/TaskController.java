package com.habits.habits_manager.task.controller;

import com.habits.habits_manager.task.dtos.TaskResponseDTO;
import com.habits.habits_manager.task.model.TaskModel;
import com.habits.habits_manager.task.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponseDTO> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO findById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDTO insert(@Valid @RequestBody TaskResponseDTO obj) {
        return service.insert(obj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TaskModel> update(@PathVariable String id, @RequestBody TaskModel obj) {
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(obj);
    }
}
