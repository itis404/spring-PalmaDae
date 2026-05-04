package org.palmadae.donortrack.controller.admin;

import jakarta.validation.Valid;
import org.palmadae.donortrack.dto.admin.DateDto;
import org.palmadae.donortrack.entity.enums.DonationStatus;
import org.palmadae.donortrack.service.event.EventService;
import org.palmadae.donortrack.service.donation.DonationService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @GetMapping()
    public String showAdminPanel(Model model) {

        model.addAttribute("dateDto", new DateDto());

        fillAdminModel(model);

        return "admin/admin";
    }

    @PostMapping("/date")
    public String showInDate(
            @Valid @ModelAttribute("dateDto") DateDto dateDto,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addFlashAttribute("dateDto", dateDto);

        redirectAttributes.addFlashAttribute("donationsByDate",
                donationService.getDonationsInDate(dateDto.getDate()));

        return "redirect:/admin";
    }

    @GetMapping("/in-progress")
    public String showInProgress(Model model) {
        fillAdminModel(model);

        return "admin/admin";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id,
                               @RequestParam DonationStatus status) {

        donationService.updateStatus(id, status);

        return "redirect:/admin/in-progress";
    }

    @PostMapping("/approve-event")
    public String approveEvent(@RequestParam Long eventId) {
        eventService.approveEvent(eventId);
        return "redirect:/admin";
    }

    @PostMapping("/reject-event")
    public String rejectEvent(@RequestParam Long eventId) {
        eventService.rejectEvent(eventId);
        return "redirect:/admin";
    }

    private void fillAdminModel(Model model) {
        model.addAttribute("inProgressDonations", donationService.getByStatus(DonationStatus.IN_PROGRESS));
        model.addAttribute("pendingEvents", eventService.getPendingEvents());
        model.addAttribute("updatedEvents", eventService.getUpdatedEvents());
    }
}