package org.palmadae.donortrack.controller.profile;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.palmadae.donortrack.dto.profile.BloodTypeChangeDto;
import org.palmadae.donortrack.dto.profile.CityChangeDto;
import org.palmadae.donortrack.dto.profile.EmailChangeDto;
import org.palmadae.donortrack.dto.profile.PasswordChangeDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.enums.BloodType;
import org.palmadae.donortrack.service.api.DadataService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/profile/edit")
public class ProfileEditController {

    @Autowired
    private UserService userService;

    @Autowired
    private DadataService dadataService;

    @GetMapping
    public String showPage(Model model, Authentication auth) {
        log.info("Profile edit page accessed");

        String username = auth.getName();
        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("User not found during profile edit page access");
                    return new RuntimeException("User not found");
                });

        model.addAttribute("currentBloodType", user.getBloodType());
        model.addAttribute("bloodTypes", BloodType.values());
        model.addAttribute("authProvider", user.getProvider());

        return "profile/edit-profile";
    }

    @PostMapping("/email")
    public String changeEmail(@Valid @ModelAttribute EmailChangeDto dto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Authentication auth) {

        if (result.hasErrors()) {
            log.warn("Email change validation failed");
            redirectAttributes.addFlashAttribute("errorMessage", "Некорретктная почта");
            return "redirect:/profile/edit";
        }

        String username = auth.getName();
        log.info("Email change requested");

        userService.changeEmail(username, dto);

        log.info("Email successfully updated");

        redirectAttributes.addFlashAttribute("successMessage", "Почта обновлена");
        return "redirect:/profile/edit";
    }

    @PostMapping("/password")
    public String changePassword(@Valid @ModelAttribute PasswordChangeDto dto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Authentication auth) {

        if (result.hasErrors() || !dto.getNewPassword().equals(dto.getConfirmPassword())) {
            log.warn("Password change validation failed");
            redirectAttributes.addFlashAttribute("errorMessage", "Пароль не совпадают");
            return "redirect:/profile/edit";
        }

        String username = auth.getName();
        log.info("Password change requested");

        userService.changePassword(username, dto);

        log.info("Password successfully updated");

        redirectAttributes.addFlashAttribute("successMessage", "Пароль изменён");
        return "redirect:/profile/edit";
    }

    @PostMapping("/bloodtype")
    public String changeBloodType(@ModelAttribute BloodTypeChangeDto dto,
                                  RedirectAttributes redirectAttributes,
                                  Authentication auth) {

        String username = auth.getName();
        log.info("Blood type update requested");

        userService.changeBloodType(username, dto.getBloodType());

        log.info("Blood type updated");

        redirectAttributes.addFlashAttribute("successMessage", "Группа крови изменена");
        return "redirect:/profile/edit";
    }

    @PostMapping("/city")
    public String changeCity(@Valid @ModelAttribute CityChangeDto dto,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Authentication auth) {

        if (result.hasErrors()) {
            log.warn("City change validation failed");
            return "redirect:/profile/edit";
        }

        log.info("City update requested");

        userService.changeCity(auth.getName(), dto.getCity().trim());

        log.info("City updated");

        redirectAttributes.addFlashAttribute("successMessage", "City updated");
        return "redirect:/profile/edit";
    }

    @GetMapping("/city/search")
    @ResponseBody
    public List<String> searchCity(@RequestParam String query) {
        log.debug("City search request received");

        return dadataService.suggestCities(query);
    }
}