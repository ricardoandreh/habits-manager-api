package com.habits.habits_manager.task.dtos;

import com.habits.habits_manager.task.enums.IconType;
import com.habits.habits_manager.task.enums.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TaskRequestDTO(
        @NotBlank(message = "O título é obrigatório")
        String title,

        String description,

        @NotNull(message = "A data de vencimento é obrigatória")
        LocalDate dueDate,

        @NotNull(message = "O ícone é obrigatório")
        IconType icon,

        @NotNull(message = "O tipo da tarefa é obrigatório")
        TaskType type,

        String location,

        @NotNull(message = "A cor é obrigatória")
        String color,

        @NotNull(message = "O status de conclusão é obrigatório")
        boolean completed
) {}