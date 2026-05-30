package org.palmadae.donortrack.controller.event;

import jakarta.validation.Valid;
import org.palmadae.donortrack.dto.event.EventDto;
import org.palmadae.donortrack.enums.EventStatus;
import org.palmadae.donortrack.entity.event.EventEntity;
import org.palmadae.donortrack.service.event.EventService;
import org.palmadae.donortrack.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @Autowired
    private DtoMapper dtoMapper;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        log.debug("Event create form requested");

        model.addAttribute("eventDto", new EventDto());
        return "main/event/create-event";
    }

    @PostMapping("/create")
    public String createEvent(
            @Valid @ModelAttribute("eventDto") EventDto eventDto,
            BindingResult bindingResult,
            Authentication auth,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            log.warn("Event creation validation failed");
            return "main/event/create-event";
        }
        log.info("Event creation requested");

        eventService.createEvent(eventDto, auth.getName());

        log.info("Event created and sent for moderation");

        redirectAttributes.addFlashAttribute(
                "success",
                "Мероприятие успешно создано и отправлено на модерацию!"
        );

        return "redirect:/events/my";
    }

    @GetMapping("/all")
    public String getAllEvents(Model model) {
        log.debug("Public events list requested");

        model.addAttribute("eventsApproved", eventService.getApprovedEvents());
        model.addAttribute("eventsUpdated", eventService.getUpdatedEvents());

        return "main/event/all-events";
    }

    @PostMapping("/join/{eventId}")
    public String joinEvent(@PathVariable Long eventId,
                            Authentication auth,
                            RedirectAttributes redirectAttributes) {

        log.info("Join event request: eventId={}", eventId);

        EventEntity event = eventService.getByEventId(eventId);

        handleJoinEvent(event, eventId, auth.getName(), redirectAttributes);

        return "redirect:/events/all";
    }

    @PostMapping("/leave/{eventId}")
    public String leaveEvent(@PathVariable Long eventId,
                             Authentication auth,
                             RedirectAttributes redirectAttributes) {

        log.info("Leave event request: eventId={}", eventId);

        eventService.leaveEvent(eventId, auth.getName());

        log.info("User left event: eventId={}", eventId);

        redirectAttributes.addFlashAttribute("success", "Вы покинули мероприятие");
        return "redirect:/events/all";
    }

    @GetMapping("/my")
    public String getMyEvents(Authentication auth, Model model) {
        log.debug("My events requested");

        model.addAttribute("events", eventService.getOrganizerEvents(auth.getName()));
        return "main/event/my-events";
    }

    @GetMapping("/edit/{eventId}")
    public String showEditForm(@PathVariable Long eventId,
                               Authentication auth,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        log.debug("Event edit form requested: eventId={}", eventId);

        EventEntity event = eventService.getEventForEdit(eventId, auth.getName());

        EventDto eventDto = dtoMapper.eventEntityToDto(event);
        model.addAttribute("eventDto", eventDto);

        return "main/event/edit-event";
    }

    @PostMapping("/edit/{eventId}")
    public String updateEvent(@PathVariable Long eventId,
                              @Valid @ModelAttribute("eventDto") EventDto dto,
                              BindingResult bindingResult,
                              Authentication auth,
                              RedirectAttributes redirectAttributes
    ) {

        log.info("Event update requested: eventId={}", eventId);

        if (bindingResult.hasErrors()) {
            log.warn("Event creation validation failed");
            return "main/event/edit-event";
        }

        eventService.updateEvent(eventId, dto, auth.getName());

        log.info("Event updated and sent for re-moderation: eventId={}", eventId);

        redirectAttributes.addFlashAttribute(
                "success",
                "Мероприятие обновлено и отправлено на повторную модерацию"
        );

        return "redirect:/events/my";
    }

    @PostMapping("/delete/{eventId}")
    public String deleteEvent(@PathVariable Long eventId,
                              Authentication auth,
                              RedirectAttributes redirectAttributes) {

        log.info("Event delete requested: eventId={}", eventId);

        eventService.deleteEvent(eventId, auth.getName());

        log.info("Event deleted: eventId={}", eventId);

        return "redirect:/events/my";
    }

    private void handleJoinEvent(EventEntity event, Long eventId, String username, RedirectAttributes redirectAttributes) {
        boolean isAvailable = event.getStatus() == EventStatus.APPROVED;

        if (isAvailable) {
            eventService.joinEvent(eventId, username);
            log.info("User joined event: eventId={}", eventId);
            redirectAttributes.addFlashAttribute("success", "Вы успешно присоединились к мероприятию");
        } else {
            log.warn("Attempt to join unavailable event: eventId={}, status={}", eventId, event.getStatus());
            redirectAttributes.addFlashAttribute("error", "Данное мероприятие недоступно для участия");
        }
    }
}