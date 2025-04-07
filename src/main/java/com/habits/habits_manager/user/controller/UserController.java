package com.habits.habits_manager.user.controller;

import com.habits.habits_manager.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.habits.habits_manager.user.dtos.user.UserResponseDTO;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @GetMapping(value = "/me")
    public UserResponseDTO getProfile(@PathVariable("id") Long id) {

        return this.userService.findById(id); 
    }
}
