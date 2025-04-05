package com.habits.habits_manager.user.dtos.user;

import com.habits.habits_manager.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotNull String firstname,
        @NotNull String lastname,
        @Size(max = 30, message = "O email deve possuir no máximo 30 caracteres") String email,
        @NotNull String password,
        @NotNull UserRole Role) {
}
