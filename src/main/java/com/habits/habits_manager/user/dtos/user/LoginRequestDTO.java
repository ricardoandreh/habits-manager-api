package com.habits.habits_manager.user.dtos.user;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(@NotNull String email, @NotNull String password) {
}
