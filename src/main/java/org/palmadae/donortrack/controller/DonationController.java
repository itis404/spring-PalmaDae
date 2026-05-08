package org.palmadae.donortrack.controller;

import jakarta.validation.Valid;
import org.palmadae.donortrack.dto.DonationDto;
import org.palmadae.donortrack.entity.DonationEntity;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.enums.DonationStatus;
import org.palmadae.donortrack.enums.DonationType;
import org.palmadae.donortrack.service.donation.DonationService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile/add-donation")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;


    @GetMapping
    public String showPage(Model model) {
        model.addAttribute("donationTypes", DonationType.values());
        model.addAttribute("donationDto", new DonationDto());
        return "profile/add-donation";
    }

    @PostMapping
    public String addDonation(
            @Valid @ModelAttribute("donationDto") DonationDto dto,
            BindingResult bindingResult,
            @RequestParam(value = "certificateFile", required = false) MultipartFile certificateFile,
            RedirectAttributes redirectAttributes,
            Authentication auth,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("donationTypes", DonationType.values());
            return "profile/add-donation";
        }

        String name = auth.getName();

        UserEntity user = userService.findByUsername(name)
                .orElseThrow(() -> new RuntimeException("User not found: " + name));

        DonationEntity donationEntity = DonationEntity.builder()
                .date(dto.getDate())
                .donationStatus(DonationStatus.IN_PROGRESS)
                .donationType(dto.getDonationType())
                .user(user)
                .build();

        donationService.saveDonation(donationEntity, certificateFile);

        return "redirect:/profile";
    }
}
