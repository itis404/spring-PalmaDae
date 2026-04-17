package org.palmadae.donortrack.controller.profile;

import jakarta.validation.Valid;
import org.palmadae.donortrack.dto.profile.ChangeBloodTypeDto;
import org.palmadae.donortrack.dto.profile.ChangeEmailDto;
import org.palmadae.donortrack.dto.profile.ChangePasswordDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.enums.BloodType;
import org.palmadae.donortrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile/edit")
public class ProfileEditController {

    @Autowired private UserService userService;

    @GetMapping
    public String showEditPage(Model model, Authentication auth) {
        String username = auth.getName();
        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("currentBloodType", user.getBloodType());
        model.addAttribute("bloodTypes", BloodType.values());
        return "edit-profile";
    }

    @PostMapping("/email")
    public String changeEmail(@Valid @ModelAttribute ChangeEmailDto dto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Authentication auth) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid email");
            return "redirect:/profile/edit";
        }
        String username = auth.getName();
        try {
            userService.changeEmail(username, dto);
            redirectAttributes.addFlashAttribute("successMessage", "Email updated");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/profile/edit";
    }

    @PostMapping("/password")
    public String changePassword(@Valid @ModelAttribute ChangePasswordDto dto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Authentication auth) {
        if (result.hasErrors() || !dto.getNewPassword().equals(dto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match or invalid");
            return "redirect:/profile/edit";
        }
        String username = auth.getName();
        try {
            userService.changePassword(username, dto);
            redirectAttributes.addFlashAttribute("successMessage", "Password changed");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/profile/edit";
    }

    @PostMapping("/bloodtype")
    public String changeBloodType(@ModelAttribute ChangeBloodTypeDto dto,
                                  RedirectAttributes redirectAttributes,
                                  Authentication auth) {
        String username = auth.getName();
        userService.changeBloodType(username, dto.getBloodType());
        redirectAttributes.addFlashAttribute("successMessage", "Blood type updated");
        return "redirect:/profile/edit";
    }
}