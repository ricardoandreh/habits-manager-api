package com.habits.habits_manager.task.service;

import com.habits.habits_manager.genericExceptions.DatabaseException;
import com.habits.habits_manager.task.dtos.TaskResponseDTO;
import com.habits.habits_manager.task.exceptions.TaskNotFoundException;
import com.habits.habits_manager.task.model.TaskModel;
import com.habits.habits_manager.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;

    public List<TaskResponseDTO> findAll() {
        List<TaskModel> taskModels =repository.findAll();
        return taskModels.stream().map(this::toTaskResponseDTO).collect(Collectors.toList());
    }

    public TaskResponseDTO findById(String id) {
        Optional<TaskModel> obj = repository.findById(id);
        TaskModel taskModel = obj.orElseThrow(() -> new TaskNotFoundException(id));
        return toTaskResponseDTO(taskModel);
    }

    public TaskResponseDTO insert(TaskResponseDTO obj) {
        TaskModel taskModel = toTaskModel(obj);
        repository.save(taskModel);
        return toTaskResponseDTO(taskModel);
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

    public TaskModel update(String id, TaskModel obj) {
        if (repository.existsById(id)) {
            TaskModel entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        }
        return null;
    }

    public void updateData(TaskModel entity, TaskModel obj) {
        entity.setTitle(obj.getTitle());
        entity.setDescription(obj.getDescription());
        entity.setDueDate(obj.getDueDate());
        entity.setCompleted(obj.isCompleted());
        entity.setType(obj.getType());
        entity.setLocation(obj.getLocation());
        entity.setCreatedAt(obj.getCreatedAt());
        entity.setUpdatedAt(obj.getUpdatedAt());
    }

    public TaskResponseDTO toTaskResponseDTO(TaskModel task) {
        return new TaskResponseDTO(
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.isCompleted(),
                task.getColor(),
                task.getIcon(),
                task.getType(),
                task.getLocation(),
                task.getCreatedAt(),
                task.getUpdatedAt());
    }

    public TaskModel toTaskModel(TaskResponseDTO taskDTO) {
        return new TaskModel(taskDTO.completed(),
                taskDTO.dueDate(),
                taskDTO.description(),
                taskDTO.title(),
                taskDTO.color(),
                taskDTO.icon(),
                taskDTO.type(),
                taskDTO.location(),
                taskDTO.createdAt(),
                taskDTO.updatedAt());
    }
}
