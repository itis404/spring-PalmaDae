package org.palmadae.donortrack.controller.auth;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.palmadae.donortrack.dto.UserDto;
import org.palmadae.donortrack.service.AuthService;
import org.palmadae.donortrack.service.mail.EmailVerificationService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        log.debug("Login page requested");
        model.addAttribute("userDto", new UserDto());
        return "auth/login";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        log.debug("Registration page requested");

        model.addAttribute("userDto", new UserDto());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registerUser(
            @Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult bindingResult,
            Model model,
            HttpSession session
    ) {

        log.info("Registration attempt started");
        if (bindingResult.hasErrors()) {
            log.warn("Registration failed due to validation errors");
            return "auth/registration";
        }

        log.info("Sending email verification code");

        authService.startRegistration(userDto);

        session.setAttribute("pendingUser", userDto);

        return "auth/confirm-code";
    }

    @PostMapping("/registration/confirm")
    public String confirmRegistration(
            @RequestParam String email,
            @RequestParam String code,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        UserDto userDto = (UserDto) session.getAttribute("pendingUser");

        if (userDto == null) {
            log.warn("Registration confirmation failed: no pending user in session");
            return "redirect:/auth/registration";
        }

        authService.confirmRegistration(email,code,userDto);

        session.removeAttribute("pendingUser");

        log.info("User successfully registered and confirmed");

        redirectAttributes.addFlashAttribute(
                "success",
                "Регистрация подтверждена! Можете войти в аккаунт"
        );

        return "redirect:/auth/login";
    }
}