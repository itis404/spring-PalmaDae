package org.palmadae.donortrack.service.event;

import org.palmadae.donortrack.dto.event.chat.ChatMessageDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.enums.EventStatus;
import org.palmadae.donortrack.entity.event.ChatMessageEntity;
import org.palmadae.donortrack.entity.event.EventChatEntity;
import org.palmadae.donortrack.entity.event.EventEntity;
import org.palmadae.donortrack.exception.custom.event.EventNotFoundException;
import org.palmadae.donortrack.exception.custom.event.chat.EventChatIsNotApprovedException;
import org.palmadae.donortrack.exception.custom.event.chat.EventChatNotFoundExceptiion;
import org.palmadae.donortrack.exception.custom.event.chat.EventChatSecurityException;
import org.palmadae.donortrack.exception.custom.user.UserNotFoundException;
import org.palmadae.donortrack.repository.chat.ChatMessageJpaRepository;
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
    private ChatMessageJpaRepository chatMessageRepository;

    @Autowired
    private UserService userService;

    public ChatMessageDto sendMessage(Long eventId, String messageText, String username) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));

        if (event.getStatus() != EventStatus.APPROVED) {
            throw new EventChatIsNotApprovedException(eventId);
        }

        UserEntity sender = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        boolean isParticipant = event.getParticipants()
                .stream()
                .anyMatch(user -> user.getId().equals(sender.getId()));

        if (!isParticipant) {
            throw new EventChatSecurityException();
        }

        EventChatEntity chat = eventChatRepository.findByEventId(eventId)
                .orElseThrow(() -> new EventChatNotFoundExceptiion(eventId));

        if (!chat.getIsActive()) {
            throw new EventChatIsNotApprovedException(eventId);
        }

        ChatMessageEntity message = ChatMessageEntity.builder()
                .chat(chat)
                .sender(sender)
                .message(messageText)
                .build();

        ChatMessageEntity saved = chatMessageRepository.save(message);

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
                .orElseThrow(() -> new EventNotFoundException(eventId));

        UserEntity user = userService.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        boolean isParticipant = event.getParticipants()
                .stream()
                .anyMatch(participant -> participant.getId().equals(user.getId()));

        if (!isParticipant) {
            throw new EventChatSecurityException();
        }

        EventChatEntity chat = eventChatRepository.findByEventId(eventId)
                .orElseThrow(() -> new EventChatNotFoundExceptiion(eventId));

        List<ChatMessageEntity> messages = chatMessageRepository.findByChatIdOrderBySentAtAsc(chat.getId());

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