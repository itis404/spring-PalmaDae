package org.palmadae.donortrack.service;

import org.palmadae.donortrack.dto.UserDto;
import org.palmadae.donortrack.service.mail.EmailVerificationService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailVerificationService emailVerificationService;

    public void startRegistration(UserDto dto) {
        validate(dto);

        emailVerificationService.sendCode(dto.getEmail());
    }

    private void validate(UserDto dto) {
        if (!dto.getPassword().equals(dto.getPassCorrect())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        if (userService.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username exists");
        }

        if (userService.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email exists");
        }
    }

    public void confirmRegistration(String email, String code, UserDto dto) {
        emailVerificationService.verifyCode(email, code);
        userService.createUserLocal(dto);
    }
}
