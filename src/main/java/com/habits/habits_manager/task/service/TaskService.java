package com.habits.habits_manager.task.service;

import com.habits.habits_manager.genericExceptions.DatabaseException;
import com.habits.habits_manager.task.exceptions.TaskNotFoundException;
import com.habits.habits_manager.task.model.Task;
import com.habits.habits_manager.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<Task> findAll() {
        return repository.findAll();
    }

    public Task findById(String id) {
        Optional<Task> obj = repository.findById(id);
        return obj.orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task insert(Task obj) {
        return repository.save(obj);
    }

    public void delete(String id) {
        try {
            if(repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                throw new TaskNotFoundException(id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Task update(String id, Task obj) {
        if (repository.existsById(id)) {
            Task entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        }
        return null;
    }

    public void updateData(Task entity, Task obj) {
        entity.setTitle(obj.getTitle());
        entity.setDescription(obj.getDescription());
        entity.setDueDate(obj.getDueDate());
        entity.setCompleted(obj.isCompleted());
        entity.setType(obj.getType());
        entity.setLocation(obj.getLocation());
        entity.setCreatedAt(obj.getCreatedAt());
        entity.setUpdatedAt(obj.getUpdatedAt());
    }
}
