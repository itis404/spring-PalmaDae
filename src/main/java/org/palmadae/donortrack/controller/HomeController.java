package org.palmadae.donortrack.controller;

import org.palmadae.donortrack.dto.donorsearch.BloodStationDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.service.api.DonorSearchService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private DonorSearchService donorSearchService;


    @GetMapping("/home")
    public String showPage(Model model, Authentication auth) {

        String identifier = auth.getName();

        UserEntity user = userService.findByUsername(identifier)
                .orElseGet(() ->
                        userService.findByYandexId(identifier)
                                .orElseThrow(() -> new RuntimeException("User not found: " + identifier))
                );

        String citySlug = toCitySlug(user.getCity());
        model.addAttribute("stations", donorSearchService.getStations(citySlug));
        return "main/home";
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