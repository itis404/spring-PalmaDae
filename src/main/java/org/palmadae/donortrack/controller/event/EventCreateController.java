package org.palmadae.donortrack.controller.event;

import org.palmadae.donortrack.dto.event.CreateEventDto;
import org.palmadae.donortrack.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events")
public class EventCreateController {
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
        try {
            String username = auth.getName();
            eventService.createEvent(eventDto, username);
            redirectAttributes.addFlashAttribute("success",
                    "Мероприятие успешно создано и отправлено на модерацию!");
            return "redirect:/events/my";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Ошибка при создании мероприятия: " + e.getMessage());
            return "redirect:/events/create";
        }
    }
}