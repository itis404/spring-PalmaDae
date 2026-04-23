package org.palmadae.donortrack.controller.auth;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.palmadae.donortrack.dto.UserDto;
import org.palmadae.donortrack.entity.EmailVerification;
import org.palmadae.donortrack.service.mail.EmailVerificationService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailVerificationService emailVerificationService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(
            @Valid @ModelAttribute("userForm") UserDto userDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            HttpSession session
    ) {

        if (!userDto.getPassword().equals(userDto.getPassCorrect())) {
            bindingResult.rejectValue("passCorrect", "error.userForm",
                    "Passwords do not match");
        }

        if (userService.existsByUsername(userDto.getUsername())) {
            bindingResult.rejectValue("username", "error.userForm",
                    "Username already exists");
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            bindingResult.rejectValue("email", "error.userForm",
                    "Email already registered");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        emailVerificationService.sendCode(userDto.getEmail());

        session.setAttribute("pendingUser", userDto);

        model.addAttribute("pendingUser", userDto);

        return "confirm-code";
    }

    @PostMapping("/registration/confirm")
    public String confirmRegistration(
            @RequestParam String email,
            @RequestParam String code,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        try {
            emailVerificationService.verifyCode(email, code);
        } catch (RuntimeException e) {
            model.addAttribute("error", "Invalid verification code. Please try again.");
            UserDto pendingUser = (UserDto) session.getAttribute("pendingUser");
            if (pendingUser != null) {
                model.addAttribute("pendingUser", pendingUser);
            }
            return "confirm-code";
        }

        UserDto userDto = (UserDto) session.getAttribute("pendingUser");

        if (userDto == null) {
            return "redirect:/auth/registration";
        }

        userService.createUser(userDto);

        session.removeAttribute("pendingUser");

        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
        return "redirect:/auth/login";
    }
}