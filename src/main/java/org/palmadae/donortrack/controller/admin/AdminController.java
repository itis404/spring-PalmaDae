package org.palmadae.donortrack.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.palmadae.donortrack.enums.DonationStatus;
import org.palmadae.donortrack.service.event.EventService;
import org.palmadae.donortrack.service.donation.DonationService;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Slf4j
@PreAuthorize("hasRole('ADMIN')")
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
        log.info("Admin panel accessed");

        fillAdminModel(model);

        return "admin/admin";
    }

    @PostMapping("/date")
    public String showInDate(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            RedirectAttributes redirectAttributes
    ) {
        log.info("Filtering donations by date: {}", date);

        redirectAttributes.addFlashAttribute(
                "donationsByDate",
                donationService.getDonationsInDate(date)
        );

        redirectAttributes.addFlashAttribute("selectedDate", date);

        return "redirect:/admin";
    }

    @GetMapping("/in-progress")
    public String showInProgress(Model model) {
        log.info("Admin requested in-progress donations view");

        fillAdminModel(model);

        return "admin/admin";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id,
                               @RequestParam String status,
                               RedirectAttributes redirectAttributes) {

        log.info("Updating donation status: donationId={}, newStatus={}", id, status);

        DonationStatus donationStatus = DonationStatus.valueOf(status);
        donationService.updateStatus(id, donationStatus);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Статус донации успешно обновлен на " + status
        );

        return "redirect:/admin/in-progress";
    }

    @PostMapping("/approve-event")
    public String approveEvent(@RequestParam Long eventId) {
        log.info("Approving event: eventId={}", eventId);

        eventService.approveEvent(eventId);
        return "redirect:/admin";
    }

    @PostMapping("/reject-event")
    public String rejectEvent(@RequestParam Long eventId) {
        log.info("Rejecting event: eventId={}", eventId);

        eventService.rejectEvent(eventId);
        return "redirect:/admin";
    }

    private void fillAdminModel(Model model) {
        model.addAttribute("inProgressDonations",
                donationService.getByStatus(DonationStatus.IN_PROGRESS));

        model.addAttribute("pendingEvents", eventService.getPendingEvents());
        model.addAttribute("updatedEvents", eventService.getUpdatedEvents());
    }
}