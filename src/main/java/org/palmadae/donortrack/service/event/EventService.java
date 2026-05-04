package org.palmadae.donortrack.service.event;

import org.palmadae.donortrack.dto.event.CreateEventDto;
import org.palmadae.donortrack.dto.event.UpdateEventDto;
import org.palmadae.donortrack.entity.event.EventChatEntity;
import org.palmadae.donortrack.entity.event.EventEntity;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.enums.EventStatus;
import org.palmadae.donortrack.exception.custom.event.*;
import org.palmadae.donortrack.exception.custom.user.UserNotFoundException;
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
                .orElseThrow(() -> new UserNotFoundException(username));

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

    public EventEntity getByEventId(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        return event;
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
                .orElseThrow(() -> new EventNotFoundException(eventId));

        eventRepository.delete(event);
        return true;
    }

    public EventEntity joinEvent(Long eventId, String username) {

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (event.getStatus() != EventStatus.APPROVED) {
            throw new EventNotApprovedException(eventId);
        }

        if (!event.hasFreeSlots()) {
            throw new EventFullException(eventId);
        }

        event.addParticipant(user);

        return eventRepository.save(event);
    }

    public EventEntity leaveEvent(Long eventId, String username) {

        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (event.getOrganizer().getId().equals(user.getId())) {
            throw new OrganizerCannotLeaveEventException();
        }

        event.removeParticipant(user);

        return eventRepository.save(event);
    }

    @Transactional
    public boolean deleteEvent(Long eventId, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (!event.getOrganizer().getUsername().equals(username) && !user.isAdmin()) {
            throw new EventSecurityException();
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

    public List<EventEntity> getUpdatedEvents() {
        return eventRepository.findByStatus(EventStatus.UPDATED);
    }

    public List<EventEntity> getOrganizerEvents(String username) {
        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return eventRepository.findByOrganizerId(user.getId());
    }

    public EventEntity getEventForEdit(Long eventId, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));

        if (!event.getOrganizer().getUsername().equals(username)) {
            throw new EventSecurityException();
        }

        return event;
    }


    public EventEntity updateEvent(Long eventId, UpdateEventDto dto, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        if (!event.getOrganizer().getUsername().equals(username)) {
            throw new EventSecurityException();
        }

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setAddress(dto.getAddress());
        event.setMaxParticipants(dto.getMaxParticipants());
        event.setStatus(EventStatus.UPDATED);
        event.setIsActive(false);

        eventChatRepository.deactivateChatByEventId(eventId);

        return eventRepository.save(event);
    }
}