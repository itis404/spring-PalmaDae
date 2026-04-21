package org.palmadae.donortrack.controller.admin;

import org.palmadae.donortrack.service.donation.DonationService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showAdminPanel() {
        return "admin";
    }

    @PostMapping("/date")
    public String showInDate() {
        return "redirect:/admin";
    }

    @PostMapping("/in-progress")
    public String showInProgress() {
        return "redirect:/admin";
    }
}
