package org.palmadae.donortrack.controller.auth;


import jakarta.validation.Valid;
import org.palmadae.donortrack.dto.UserDto;
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
            RedirectAttributes redirectAttributes
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

        boolean created = userService.createUser(userDto);
        if (!created) {
            bindingResult.rejectValue("username", "error.userForm",
                    "Registration failed, please try again");
            return "registration";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Registration successful!");
        return "redirect:/auth/login";
    }


}
