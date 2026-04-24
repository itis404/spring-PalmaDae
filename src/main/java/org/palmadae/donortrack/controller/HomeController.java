package org.palmadae.donortrack.controller;

import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.service.DonorSearchService;
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

    @Autowired
    private DonorSearchService donorSearchService;


    @GetMapping("/home")
    public String showPage(Model model, Authentication auth) {

        String username = auth.getName();

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        String role = String.valueOf(user.getRole());

        String citySlug = toCitySlug(user.getCity());

        var stations = donorSearchService.getStations(citySlug);

        stations = stations.stream()
                .filter(s -> s.closed == null || !s.closed)
                .toList();

        model.addAttribute("username", username);
        model.addAttribute("role", role);
        model.addAttribute("stations", stations);

        return "home";
    }

    private String toCitySlug(String city) {
        if (city == null) return null;

        return switch (city.toLowerCase()) {
            case "москва" -> "moscow";
            case "казань" -> "kazan";
            case "санкт-петербург" -> "saint-petersburg";
            default -> city.toLowerCase();
        };
    }
}