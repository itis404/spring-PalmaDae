package org.palmadae.donortrack.service.event;

import org.palmadae.donortrack.dto.event.CreateEventDto;
import org.palmadae.donortrack.dto.event.UpdateEventDto;
import org.palmadae.donortrack.entity.event.EventChatEntity;
import org.palmadae.donortrack.entity.event.EventEntity;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.enums.EventStatus;
import org.palmadae.donortrack.repository.event.EventChatRepository;
import org.palmadae.donortrack.repository.event.EventJpaRepository;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        List<UserEntity> participants = new ArrayList<>();
        participants.add(organizer);

        EventEntity event = EventEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .eventDate(dto.getEventDate())
                .address(dto.getAddress())
                .maxParticipants(dto.getMaxParticipants())
                .currentParticipants(1)
                .isActive(true)
                .status(EventStatus.PENDING)
                .organizer(organizer)
                .participants(participants)
                .build();

        EventEntity savedEvent = eventRepository.save(event);

        EventChatEntity chat = EventChatEntity.builder()
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
            event.setIsActive(true);

            eventRepository.save(event);
            eventChatRepository.activateChatByEventId(eventId);

            return true;
        }

        return false;
    }

    @Transactional
    public boolean rejectEvent(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));

        eventRepository.delete(event);
        return true;
    }

    public EventEntity joinEvent(Long eventId, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (event.getStatus() != EventStatus.APPROVED) {
            throw new RuntimeException("Нельзя вступить в неопубликованное мероприятие");
        }

        if (!event.hasFreeSlots()) {
            throw new RuntimeException("Нет свободных мест");
        }

        event.addParticipant(user);

        return eventRepository.save(event);
    }

    public EventEntity leaveEvent(Long eventId, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (event.getOrganizer().getId().equals(user.getId())) {
            throw new RuntimeException("Создатель не может покинуть своё мероприятие");
        }

        event.removeParticipant(user);

        return eventRepository.save(event);
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

    public List<EventEntity> getApprovedEvents() {
        return eventRepository.findByStatusAndEventDateAfterOrderByEventDateAsc(
                EventStatus.APPROVED,
                java.time.LocalDateTime.now()
        );
    }
    public List<EventEntity> getPendingEvents() {
        return eventRepository.findByStatus(EventStatus.PENDING);
    }
    public List<EventEntity> getOrganizerEvents(String username) {
        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return eventRepository.findByOrganizerId(user.getId());
    }

    public EventEntity getEventForEdit(Long eventId, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));

        if (!event.getOrganizer().getUsername().equals(username)) {
            throw new SecurityException("Нет доступа");
        }

        return event;
    }


    public EventEntity updateEvent(Long eventId, UpdateEventDto dto, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));

        if (!event.getOrganizer().getUsername().equals(username)) {
            throw new SecurityException("Редактировать может только создатель");
        }

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setAddress(dto.getAddress());
        event.setMaxParticipants(dto.getMaxParticipants());
        event.setStatus(org.palmadae.donortrack.entity.enums.EventStatus.PENDING);
        event.setIsActive(false);

        eventChatRepository.deactivateChatByEventId(eventId);

        return eventRepository.save(event);
    }
}