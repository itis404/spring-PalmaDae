package org.palmadae.donortrack.controller.profile;

import jakarta.validation.Valid;
import org.palmadae.donortrack.dto.profile.BloodTypeChangeDto;
import org.palmadae.donortrack.dto.profile.CityChangeDto;
import org.palmadae.donortrack.dto.profile.EmailChangeDto;
import org.palmadae.donortrack.dto.profile.PasswordChangeDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.enums.BloodType;
import org.palmadae.donortrack.service.user.DadataService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/profile/edit")
public class ProfileEditController {
    @Autowired
    private UserService userService;

    @Autowired
    private DadataService dadataService;

    @GetMapping
    public String showPage(Model model, Authentication auth) {
        String username = auth.getName();
        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        model.addAttribute("currentBloodType", user.getBloodType());
        model.addAttribute("bloodTypes", BloodType.values());
        return "edit-profile";
    }

    @PostMapping("/email")
    public String changeEmail(@Valid @ModelAttribute EmailChangeDto dto,
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
    public String changePassword(@Valid @ModelAttribute PasswordChangeDto dto,
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
    public String changeBloodType(@ModelAttribute BloodTypeChangeDto dto,
                                  RedirectAttributes redirectAttributes,
                                  Authentication auth) {
        String username = auth.getName();
        userService.changeBloodType(username, dto.getBloodType());
        redirectAttributes.addFlashAttribute("successMessage", "Blood type updated");
        return "redirect:/profile/edit";
    }

    @PostMapping("/city")
    public String changeCity(@Valid @ModelAttribute CityChangeDto dto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Authentication auth) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "City cannot be empty");
            return "redirect:/profile/edit";
        }

        userService.changeCity(auth.getName(), dto.getCity().trim());

        redirectAttributes.addFlashAttribute("successMessage", "City updated");
        return "redirect:/profile/edit";
    }

    @GetMapping("/city/search")
    @ResponseBody
    public List<String> searchCity(@RequestParam String query) {
        return dadataService.suggestCities(query);
    }
}