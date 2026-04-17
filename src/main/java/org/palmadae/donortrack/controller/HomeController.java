package org.palmadae.donortrack.controller;

import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;


    @GetMapping("/home")
    public String showPage(Model model) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

        String username = auth.getName();

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        String role = String.valueOf(user.getRole());

        model.addAttribute("username", username);
        model.addAttribute("role", role);

        return "home";
    }

}