package org.palmadae.donortrack.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/certificate")
public class CertificateController {
    @GetMapping
    public String showPage() {
        return "certificate";
    }
}
