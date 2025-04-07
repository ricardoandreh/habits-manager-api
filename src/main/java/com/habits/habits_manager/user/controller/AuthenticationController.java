package com.habits.habits_manager.user.controller;

import com.habits.habits_manager.user.dtos.jwt.AccessResponseDTO;
import com.habits.habits_manager.user.dtos.user.LoginRequestDTO;
import com.habits.habits_manager.user.dtos.user.RegisterRequestDTO;
import com.habits.habits_manager.user.enums.UserRole;
import com.habits.habits_manager.user.model.UserModel;
import com.habits.habits_manager.user.repository.UserRepository;
import com.habits.habits_manager.user.service.AuthServiceImpl;
import com.habits.habits_manager.user.service.TokenService;
import com.habits.habits_manager.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthServiceImpl authService;

    private final UserService userService;

    private final UserRepository repository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginRequestDTO data) {
        UserModel user = (UserModel) repository.findByEmail(data.email());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User doesn't exists");
        }

        if (!passwordEncoder.matches(data.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user.getPassword() + data.password() + "Invalid login or password");
        }

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateAccessToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new AccessResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDTO data) {
        if (this.repository.findByEmail(data.email()) != null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Users already exists");

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        UserModel user = new UserModel(data.firstname(), data.lastname(), data.email(), encryptedPassword, UserRole.USER);
        
        this.repository.save(user);

        return ResponseEntity.ok().body("Usuário criado com sucesso!");

    }
}
