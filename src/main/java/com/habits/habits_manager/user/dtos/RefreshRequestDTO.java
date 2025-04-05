package com.habits.habits_manager.user.dtos;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequestDTO(@NotBlank String refreshToken) {
}