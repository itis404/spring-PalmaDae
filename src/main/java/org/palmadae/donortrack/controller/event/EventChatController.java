package org.palmadae.donortrack.controller.event;

import org.palmadae.donortrack.dto.event.chat.SendMessageDto;
import org.palmadae.donortrack.service.event.EventChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/events/chat")
public class EventChatController {

    @Autowired
    private EventChatService eventChatService;

    @GetMapping("/{eventId}")
    public String openChat(@PathVariable Long eventId,
                           Authentication auth,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            String username = auth.getName();

            model.addAttribute("eventId", eventId);
            model.addAttribute("messages", eventChatService.getMessages(eventId, username));
            model.addAttribute("sendMessageDto", new SendMessageDto());

            return "main/event/event-chat";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/events/all";
        }
    }

    @PostMapping("/{eventId}/send")
    public String sendMessage(@PathVariable Long eventId,
                              @ModelAttribute SendMessageDto sendMessageDto,
                              Authentication auth,
                              RedirectAttributes redirectAttributes) {
        try {
            String username = auth.getName();

            if (sendMessageDto.getMessage() == null || sendMessageDto.getMessage().trim().isEmpty()) {
                return "redirect:/events/chat/" + eventId;
            }

            eventChatService.sendMessage(eventId, sendMessageDto.getMessage(), username);

            return "redirect:/events/chat/" + eventId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/events/chat/" + eventId;
        }
    }
}