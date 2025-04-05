package com.habits.habits_manager.task.dtos;

import com.habits.habits_manager.task.enums.IconType;
import com.habits.habits_manager.task.enums.TaskType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskResponseDTO(
        String title,
        String description,
        LocalDate dueDate,
        boolean completed,
        String color,
        IconType icon,
        TaskType type,
        String location,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}