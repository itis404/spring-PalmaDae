package org.palmadae.donortrack.controller;


import jakarta.validation.Valid;
import org.palmadae.donortrack.dto.UserDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userForm", new UserDto());
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
            RedirectAttributes redirectAttributes
    ) {
        if (!userDto.getPassword().equals(userDto.getPassCorrect())) {
            bindingResult.rejectValue("passCorrect", "error.userForm",
                    "Passwords do not match");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        UserEntity user = UserEntity.builder()
                .login(userDto.getLogin())
                .hash_pass(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();

        userService.createUser(user);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Registration successful!"
        );

        return "redirect:/auth/registration";
    }


}
