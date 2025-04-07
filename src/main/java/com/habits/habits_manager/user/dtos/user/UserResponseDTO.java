package com.habits.habits_manager.user.dtos.user;

import com.habits.habits_manager.user.enums.UserRole;

public record UserResponseDTO(
        String firstname,
        String lastname,
        String email,
        String password,
        UserRole Role) {
}
