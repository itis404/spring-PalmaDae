package org.palmadae.donortrack.controller.event;

import org.palmadae.donortrack.dto.event.ChatMessageDto;
import org.palmadae.donortrack.service.event.EventChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/events/chat")
public class EventChatController {
    @Autowired
    private EventChatService eventChatService;

    @GetMapping("/{eventId}")
    public String openChat(@PathVariable Long eventId,
                           Authentication auth,
                           Model model) {

        fillEventChatModel(model, auth, eventId);

        return "main/event/event-chat";
    }

    @PostMapping("/{eventId}/send")
    @ResponseBody
    public String sendMessage(@PathVariable Long eventId,
                              @RequestParam String message,
                              Authentication auth) {
        String username = auth.getName();

        if (message == null || message.trim().isEmpty()) {
            return "empty";
        }

        eventChatService.sendMessage(eventId, message, username);
        return "ok";
    }

    @GetMapping("/{eventId}/messages")
    @ResponseBody
    public Object getMessages(@PathVariable Long eventId,
                              Authentication auth) {
        String username = auth.getName();
        return eventChatService.getMessages(eventId, username);
    }

    private void fillEventChatModel(Model model, Authentication auth, Long eventId) {
        String username = auth.getName();
        model.addAttribute("eventId", eventId);
        model.addAttribute("messages",
                eventChatService.getMessages(eventId, username));
        model.addAttribute("sendMessageDto", new ChatMessageDto());
    }
}
