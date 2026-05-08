package org.palmadae.donortrack.controller.auth;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.palmadae.donortrack.dto.UserDto;
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
    private UserService userService;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @GetMapping("/login")
    public String showLoginForm() {
        log.debug("Login page requested");
        return "auth/login";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        log.debug("Registration page requested");

        model.addAttribute("userForm", new UserDto());
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registerUser(
            @Valid @ModelAttribute("userForm") UserDto userDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session
    ) {

        log.info("Registration attempt started");

        if (!userDto.getPassword().equals(userDto.getPassCorrect())) {
            log.warn("Registration validation failed: password mismatch");
            bindingResult.rejectValue("passCorrect", "error.userForm",
                    "Passwords do not match");
        }

        if (userService.existsByUsername(userDto.getUsername())) {
            log.warn("Registration blocked: username already exists");
            bindingResult.rejectValue("username", "error.userForm",
                    "Username already exists");
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            log.warn("Registration blocked: email already registered");
            bindingResult.rejectValue("email", "error.userForm",
                    "Email already registered");
        }

        if (bindingResult.hasErrors()) {
            log.warn("Registration failed due to validation errors");
            return "auth/registration";
        }

        log.info("Sending email verification code");

        emailVerificationService.sendCode(userDto.getEmail());

        session.setAttribute("pendingUser", userDto);

        log.info("Registration pending verification code sent");

        model.addAttribute("pendingUser", userDto);

        return "auth/confirm-code";
    }

    @PostMapping("/registration/confirm")
    public String confirmRegistration(
            @RequestParam String email,
            @RequestParam String code,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        log.info("Email verification attempt");

        emailVerificationService.verifyCode(email, code);

        UserDto userDto = (UserDto) session.getAttribute("pendingUser");

        if (userDto == null) {
            log.warn("Registration confirmation failed: no pending user in session");
            return "redirect:/auth/registration";
        }

        userService.createUserLocal(userDto);

        session.removeAttribute("pendingUser");

        log.info("User successfully registered and confirmed");

        redirectAttributes.addFlashAttribute(
                "success",
                "Регистрация подтверждена! Можете войти в аккаунт"
        );

        return "redirect:/auth/login";
    }
}