package org.palmadae.donortrack.controller.profile;


import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;


    @GetMapping("")
    public String showProfile(Model model) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String role = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        model.addAttribute("username", username);
        model.addAttribute("role", role);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("bloodType", user.getBloodType() != null ? user.getBloodType().name() : "не указана");

        return "profile";
    }
}
