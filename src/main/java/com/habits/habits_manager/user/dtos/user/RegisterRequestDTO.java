package com.habits.habits_manager.user.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email @Size(max = 30, message = "O email deve possuir no máximo 30 caracteres") String email,
        @NotBlank String password) {
}
