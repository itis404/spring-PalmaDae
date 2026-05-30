package org.palmadae.donortrack.controller.profile;

import org.palmadae.donortrack.dto.profile.ProfileDto;
import org.palmadae.donortrack.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping
    public String showPage(Authentication auth, Model model) {

        ProfileDto profile = profileService.getProfile(auth.getName());

        model.addAttribute("profile", profile);

        return "profile/profile";
    }
}