package org.palmadae.donortrack.controller.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.palmadae.donortrack.dto.event.CreateEventDto;
import org.palmadae.donortrack.dto.event.UpdateEventDto;
import org.palmadae.donortrack.entity.event.EventEntity;
import org.palmadae.donortrack.service.event.EventService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Event REST API", description = "API для управления мероприятиями")
public class EventRestController {

    private final EventService eventService;

    @Operation(summary = "Получить все одобренные мероприятия")
    @GetMapping
    public List<EventEntity> getAllApproved() {
        return eventService.getApprovedEvents();
    }

    @Operation(summary = "Получить мои мероприятия")
    @GetMapping("/my")
    public List<EventEntity> getMyEvents(Authentication auth) {
        return eventService.getOrganizerEvents(auth.getName());
    }

    @Operation(summary = "Создать мероприятие")
    @PostMapping
    public EventEntity create(@RequestBody CreateEventDto dto, Authentication auth) {
        return eventService.createEvent(dto, auth.getName());
    }

    @Operation(summary = "Обновить мероприятие")
    @PutMapping("/{id}")
    public EventEntity update(@PathVariable Long id,
                              @RequestBody UpdateEventDto dto,
                              Authentication auth) {
        return eventService.updateEvent(id, dto, auth.getName());
    }

    @Operation(summary = "Удалить мероприятие")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Authentication auth) {
        eventService.deleteEvent(id, auth.getName());
        return "Мероприятие удалено";
    }

    @Operation(summary = "Присоединиться к мероприятию")
    @PostMapping("/{id}/join")
    public EventEntity join(@PathVariable Long id, Authentication auth) {
        return eventService.joinEvent(id, auth.getName());
    }

    @Operation(summary = "Покинуть мероприятие")
    @PostMapping("/{id}/leave")
    public EventEntity leave(@PathVariable Long id, Authentication auth) {
        return eventService.leaveEvent(id, auth.getName());
    }
}