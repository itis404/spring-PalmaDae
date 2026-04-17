package org.palmadae.donortrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile/add-donation")
public class DonationController {
    @GetMapping
    public String showPage() {
        return "add-donation";
    }
}
