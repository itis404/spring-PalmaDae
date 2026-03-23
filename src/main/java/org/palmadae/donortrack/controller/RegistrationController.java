package org.palmadae.donortrack.controller;


import org.palmadae.donortrack.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registrationModel(Model model) {
        model.addAttribute();
        return "registration";
    }
}
