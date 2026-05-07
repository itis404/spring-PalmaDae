package org.palmadae.donortrack.controller.profile;


import jakarta.validation.Valid;
import org.palmadae.donortrack.dto.profile.ProfileDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.exception.custom.user.UserNotFoundException;
import org.palmadae.donortrack.service.donation.DonationService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private DonationService donationService;

    @GetMapping("")
    public String showPage(Authentication auth, Model model) {

        String username = auth.getName();

        UserEntity user = userService.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        ProfileDto profile = ProfileDto.builder()
                .username(user.getUsername())
                .bloodType(user.getBloodType() != null ? user.getBloodType().name() : "не указана")
                .city(user.getCity() != null ? user.getCity() : "не указан")
                .donations(donationService.getUserDonations(user.getId()))
                .build();

        model.addAttribute("profile", profile);

        return "profile/profile";
    }
}
