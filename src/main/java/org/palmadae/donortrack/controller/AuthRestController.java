package org.palmadae.donortrack.controller;

import jakarta.validation.Valid;
import org.palmadae.donortrack.dto.UserDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            return ResponseEntity.badRequest().body(errors);
        }

        if (!userDto.getPassword().equals(userDto.getPassCorrect())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("passCorrect", "Passwords do not match"));
        }

        if (userService.existsByUsername(userDto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("username", "Username already taken"));
        }
        if (userService.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("email", "Email already registered"));
        }

        UserEntity user = UserEntity.builder()
                .username(userDto.getUsername())
                .hash_pass(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();

        userService.createUser(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }
}