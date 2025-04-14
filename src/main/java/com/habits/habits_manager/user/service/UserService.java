package com.habits.habits_manager.user.service;

import com.habits.habits_manager.user.dtos.user.UserResponseDTO;
import com.habits.habits_manager.user.exceptions.UserNotFoundException;
import com.habits.habits_manager.user.model.UserModel;
import com.habits.habits_manager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;

    public UserResponseDTO findByEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        UserDetails user = this.userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        return this.toUserDTO((UserModel) user);
    }

    public UserResponseDTO toUserDTO(UserModel user) {
        return new UserResponseDTO(user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }
}
