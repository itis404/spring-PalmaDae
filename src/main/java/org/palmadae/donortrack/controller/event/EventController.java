package org.palmadae.donortrack.controller.event;

import org.palmadae.donortrack.dto.event.CreateEventDto;
import org.palmadae.donortrack.dto.event.UpdateEventDto;
import org.palmadae.donortrack.service.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("eventDto", new CreateEventDto());
        return "main/event/create-event";
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute CreateEventDto eventDto,
                              Authentication auth,
                              RedirectAttributes redirectAttributes) {
        eventService.createEvent(eventDto, auth.getName());
        redirectAttributes.addFlashAttribute("success",
                    "Мероприятие успешно создано и отправлено на модерацию!");
        return "redirect:/events/my";
    }

    @GetMapping("/all")
    public String getAllEvents(Model model) {
        model.addAttribute("events", eventService.getApprovedEvents());
        return "main/event/all-events";
    }

    @PostMapping("/join/{eventId}")
    public String joinEvent(@PathVariable Long eventId,
                            Authentication auth,
                            RedirectAttributes redirectAttributes) {
        eventService.joinEvent(eventId, auth.getName());
        redirectAttributes.addFlashAttribute("success", "Вы успешно присоединились к мероприятию");
        return "redirect:/events/all";
    }

    @PostMapping("/leave/{eventId}")
    public String leaveEvent(@PathVariable Long eventId,
                             Authentication auth,
                             RedirectAttributes redirectAttributes) {
        eventService.leaveEvent(eventId, auth.getName());
        redirectAttributes.addFlashAttribute("success", "Вы покинули мероприятие");
        return "redirect:/events/all";
    }

    @GetMapping("/my")
    public String getMyEvents(Authentication auth, Model model) {
        model.addAttribute("events", eventService.getOrganizerEvents(auth.getName()));
        return "main/event/my-events";
    }

    @GetMapping("/edit/{eventId}")
    public String showEditForm(@PathVariable Long eventId,
                               Authentication auth,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        model.addAttribute("event", eventService.getEventForEdit(eventId, auth.getName()));
        return "main/event/edit-event";
    }

    @PostMapping("/edit/{eventId}")
    public String updateEvent(@PathVariable Long eventId,
                              @ModelAttribute UpdateEventDto dto,
                              Authentication auth,
                              RedirectAttributes redirectAttributes) {
        eventService.updateEvent(eventId, dto, auth.getName());
        redirectAttributes.addFlashAttribute("success",
                "Мероприятие обновлено и отправлено на повторную модерацию");
        return "redirect:/events/my";
    }

    @PostMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable Long eventId, Authentication auth, RedirectAttributes redirectAttributes) {
        eventService.deleteEvent(eventId, auth.getName());
        return "redirect:/events/my";
    }
}