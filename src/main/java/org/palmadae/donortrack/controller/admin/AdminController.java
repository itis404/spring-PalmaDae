package org.palmadae.donortrack.controller.admin;

import org.palmadae.donortrack.entity.enums.DonationStatus;
import org.palmadae.donortrack.service.donation.DonationService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public String showAdminPanel(Model model) {

        model.addAttribute(
                "donations",
                donationService.getByStatus(DonationStatus.IN_PROGRESS)
        );

        return "admin";
    }

    @PostMapping("/date")
    public String showInDate() {
        return "redirect:/admin";
    }

    @GetMapping("/in-progress")
    public String showInProgress(Model model) {
        model.addAttribute(
                "donations",
                donationService.getByStatus(DonationStatus.IN_PROGRESS)
        );
        return "admin";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id,
                               @RequestParam DonationStatus status) {

        donationService.updateStatus(id, status);

        return "redirect:/admin/in-progress";
    }
}
