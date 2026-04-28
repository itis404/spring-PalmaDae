package org.palmadae.donortrack.dto.event.chat;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChatMessageDto {
    private Long id;
    private String senderUsername;
    private String message;
    private LocalDateTime sentAt;
}