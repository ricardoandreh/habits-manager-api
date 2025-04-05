package com.habits.habits_manager.user.dtos.jwt;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequestDTO(@NotBlank String refreshToken) {
}