package com.habits.habits_manager.user.controller;

import java.util.Optional;
import com.habits.habits_manager.user.dtos.jwt.AccessResponseDTO;
import com.habits.habits_manager.user.dtos.user.LoginRequestDTO;
import com.habits.habits_manager.user.dtos.user.RegisterRequestDTO;
import com.habits.habits_manager.user.enums.UserRole;
import com.habits.habits_manager.user.model.UserModel;
import com.habits.habits_manager.user.repository.UserRepository;
import com.habits.habits_manager.user.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AccessResponseDTO login(@RequestBody @Valid LoginRequestDTO data) {
        UserDetails user = this.userRepository.findByEmail(data.email())
            .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));

        if (!passwordEncoder.matches(data.password(), user.getPassword())) {
            throw new RuntimeException("Invalid login or password");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateAccessToken((UserModel) auth.getPrincipal());

        return new AccessResponseDTO(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody @Valid RegisterRequestDTO data) {
        this.userRepository.findByEmail(data.email()).ifPresent(user -> {
            throw new RuntimeException("Usuário já existe");
        });

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        UserModel user = new UserModel();

        BeanUtils.copyProperties(data, user);
        user.setPassword(encryptedPassword);
        user.setRole(UserRole.USER);
        
        this.userRepository.save(user);

        return "Usuário criado com sucesso!";
    }
}
