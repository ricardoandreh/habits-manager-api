package com.habits.habits_manager.user.dtos.user;

import com.habits.habits_manager.user.enums.UserRole;
import jakarta.validation.constraints.NotBlank;

public record UserResponseDTO(
        @NotBlank String firstname,
        @NotBlank String lastname,
        @NotBlank String email,
        @NotBlank String password,
        @NotBlank UserRole Role) {
}
