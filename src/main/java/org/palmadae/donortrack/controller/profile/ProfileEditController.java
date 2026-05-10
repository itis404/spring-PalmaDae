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
        fillProfileModel(model, auth);
        return "profile/edit-profile";
    }

    @PostMapping("/email")
    public String changeEmail(@Valid @ModelAttribute("emailChangeDto") EmailChangeDto dto,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Authentication auth) {

        if (result.hasErrors()) {
            log.warn("Email change validation failed: {}", result.getAllErrors());
            redirectAttributes.addFlashAttribute("errorMessage", "Некорректная почта");
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
    public String changePassword(@Valid @ModelAttribute("passwordChangeDto") PasswordChangeDto dto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes,
                                 Authentication auth) {
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword", "Пароли не совпадают");
        }

        if (result.hasErrors()) {
            log.warn("Password change validation failed: {}", result.getAllErrors());
            redirectAttributes.addFlashAttribute("errorMessage", "Проверьте правильность заполнения полей");
            return "redirect:/profile/edit";
        }

        String username = auth.getName();
        log.info("Password change requested");

        try {
            userService.changePassword(username, dto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Неверный старый пароль");
            return "redirect:/profile/edit";
        }

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

    private void fillProfileModel(Model model, Authentication auth) {
        String username = auth.getName();
        UserEntity user = userService.findByUsername(username);

        model.addAttribute("currentBloodType", user.getBloodType());
        model.addAttribute("bloodTypes", BloodType.values());
        model.addAttribute("authProvider", user.getProvider());
        model.addAttribute("emailChangeDto", new EmailChangeDto());
        model.addAttribute("passwordChangeDto", new PasswordChangeDto());
        model.addAttribute("bloodTypeChangeDto", new BloodTypeChangeDto());
        model.addAttribute("cityChangeDto", new CityChangeDto());
    }
}