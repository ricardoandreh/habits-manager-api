package com.habits.habits_manager.user.dtos.user;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank String email, @NotBlank String password) {
}
