package com.habits.habits_manager.task.service;

import com.habits.habits_manager.genericExceptions.DatabaseException;
import com.habits.habits_manager.task.dtos.TaskRequestDTO;
import com.habits.habits_manager.task.dtos.TaskResponseDTO;
import com.habits.habits_manager.task.exceptions.TaskNotFoundException;
import com.habits.habits_manager.task.model.TaskModel;
import com.habits.habits_manager.user.model.UserModel;
import com.habits.habits_manager.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.habits.habits_manager.user.repository.UserRepository;
import com.habits.habits_manager.task.dtos.TaskUpdateDTO;
import com.habits.habits_manager.task.utils.Utils;

@Service
@RequiredArgsConstructor
public class TaskService {

    final private TaskRepository taskRepository;
    final private UserRepository userRepository;
    
    public List<TaskResponseDTO> findAll() {
        List<TaskModel> taskModels = taskRepository.findAll();
        return taskModels.stream().map(this::toTaskResponseDTO).collect(Collectors.toList());
    }

    public TaskResponseDTO findById(Long id) {
        Optional<TaskModel> obj = taskRepository.findById(id);
        TaskModel taskModel = obj.orElseThrow(() -> new TaskNotFoundException(id));
        return toTaskResponseDTO(taskModel);
    }

    public TaskResponseDTO insert(TaskRequestDTO obj) {
        TaskModel taskModel = new TaskModel();

        BeanUtils.copyProperties(obj, taskModel);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        UserDetails user = this.userRepository.findByEmail(email);
        
        BeanUtils.copyProperties(obj, taskModel);

        taskModel.setUser((UserModel) user);
        
        taskRepository.save(taskModel);
        return toTaskResponseDTO(taskModel);
    }

    public void delete(Long id) {
        try {
            if(taskRepository.existsById(id)) {
                taskRepository.deleteById(id);
            } else {
                throw new TaskNotFoundException(id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public TaskResponseDTO update(Long id, TaskUpdateDTO obj) {
        TaskModel task = this.taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        Utils.copyNonNullProperties(obj, task);

        return toTaskResponseDTO(taskRepository.save(task));
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
}
