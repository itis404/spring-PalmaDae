package org.palmadae.donortrack.service.event;

import org.palmadae.donortrack.dto.event.chat.ChatMessageDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.entity.enums.EventStatus;
import org.palmadae.donortrack.entity.event.ChatMessage;
import org.palmadae.donortrack.entity.event.EventChatEntity;
import org.palmadae.donortrack.entity.event.EventEntity;
import org.palmadae.donortrack.repository.chat.ChatMessageRepository;
import org.palmadae.donortrack.repository.event.EventChatRepository;
import org.palmadae.donortrack.repository.event.EventJpaRepository;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventChatService {

    @Autowired
    private EventJpaRepository eventRepository;

    @Autowired
    private EventChatRepository eventChatRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserService userService;

    public ChatMessageDto sendMessage(Long eventId, String messageText, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));

        if (event.getStatus() != EventStatus.APPROVED) {
            throw new RuntimeException("Чат недоступен до одобрения мероприятия");
        }

        UserEntity sender = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        boolean isParticipant = event.getParticipants()
                .stream()
                .anyMatch(user -> user.getId().equals(sender.getId()));

        if (!isParticipant) {
            throw new SecurityException("Только участники мероприятия могут писать в чат");
        }

        EventChatEntity chat = eventChatRepository.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Чат мероприятия не найден"));

        if (!chat.getIsActive()) {
            throw new RuntimeException("Чат отключён");
        }

        ChatMessage message = ChatMessage.builder()
                .chat(chat)
                .sender(sender)
                .message(messageText)
                .build();

        ChatMessage saved = chatMessageRepository.save(message);

        return ChatMessageDto.builder()
                .id(saved.getId())
                .senderUsername(saved.getSender().getUsername())
                .message(saved.getMessage())
                .sentAt(saved.getSentAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ChatMessageDto> getMessages(Long eventId, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Мероприятие не найдено"));

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        boolean isParticipant = event.getParticipants()
                .stream()
                .anyMatch(participant -> participant.getId().equals(user.getId()));

        if (!isParticipant) {
            throw new SecurityException("Доступ к чату только для участников мероприятия");
        }

        EventChatEntity chat = eventChatRepository.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Чат мероприятия не найден"));

        List<ChatMessage> messages = chatMessageRepository.findByChatIdOrderBySentAtAsc(chat.getId());

        return messages.stream()
                .map(msg -> ChatMessageDto.builder()
                        .id(msg.getId())
                        .senderUsername(msg.getSender().getUsername())
                        .message(msg.getMessage())
                        .sentAt(msg.getSentAt())
                        .build())
                .collect(Collectors.toList());
    }
}