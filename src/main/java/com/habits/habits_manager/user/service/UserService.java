package com.habits.habits_manager.user.service;

import com.habits.habits_manager.user.dtos.user.UserResponseDTO;
import com.habits.habits_manager.user.exceptions.UserNotFoundException;
import com.habits.habits_manager.user.model.UserModel;
import com.habits.habits_manager.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;

    public UserResponseDTO findById(Long id) {
        UserModel user = this.userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

        return this.toUserDTO(user);
    }

    public UserResponseDTO toUserDTO(UserModel user) {
        return new UserResponseDTO(user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole());
    }

    // public UserModel toUserModel(UserResponseDTO userDTO) {
    //     return new UserModel(userDTO.firstname(),
    //             userDTO.lastname(),
    //             userDTO.email(),
    //             userDTO.password(),
    //             userDTO.Role());
    // }
}
