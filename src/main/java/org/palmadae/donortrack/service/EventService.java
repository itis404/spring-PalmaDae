package org.palmadae.donortrack.service;

import org.palmadae.donortrack.dto.event.CreateEventDto;
import org.palmadae.donortrack.dto.event.UpdateEventDto;
import org.palmadae.donortrack.entity.event.EventChat;
import org.palmadae.donortrack.entity.event.EventEntity;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.enums.EventStatus;
import org.palmadae.donortrack.repository.event.EventChatRepository;
import org.palmadae.donortrack.repository.event.EventJpaRepository;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventJpaRepository eventRepository;

    @Autowired
    private EventChatRepository eventChatRepository;

    @Autowired
    private UserService userService;

    public EventEntity createEvent(CreateEventDto dto, String username) {
        UserEntity organizer = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        EventEntity event = EventEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .address(dto.getAddress())
                .maxParticipants(dto.getMaxParticipants())
                .currentParticipants(0)
                .isActive(true)
                .status(EventStatus.PENDING)
                .organizer(organizer)
                .participants(new java.util.ArrayList<>())
                .build();

        EventEntity savedEvent = eventRepository.save(event);

        EventChat chat = EventChat.builder()
                .event(savedEvent)
                .isActive(true)
                .build();
        eventChatRepository.save(chat);

        savedEvent.setChat(chat);

        return eventRepository.save(savedEvent);
    }

    public boolean approveEvent(Long eventId) {
        Optional<EventEntity> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isPresent()) {
            EventEntity event = eventOpt.get();
            event.setStatus(EventStatus.APPROVED);
            eventRepository.save(event);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteEvent(Long eventId, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (!event.getOrganizer().getUsername().equals(username) && !user.isAdmin()) {
            throw new SecurityException("Нет прав для удаления мероприятия");
        }

        eventRepository.delete(event);
        return true;
    }
}