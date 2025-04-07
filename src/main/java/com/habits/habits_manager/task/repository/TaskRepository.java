package com.habits.habits_manager.task.repository;

import java.util.List;

import com.habits.habits_manager.task.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {
    
    List<TaskModel> findByUserEmail(String email);
}