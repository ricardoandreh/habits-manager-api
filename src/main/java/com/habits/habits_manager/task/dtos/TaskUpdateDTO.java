package com.habits.habits_manager.task.dtos;

import com.habits.habits_manager.task.enums.IconType;
import com.habits.habits_manager.task.enums.TaskType;

import java.time.LocalDate;

public record TaskUpdateDTO(
        String title,

        String description,

        LocalDate dueDate,

        IconType icon,

        TaskType type,

        String location,

        String color,

        boolean completed
) {}