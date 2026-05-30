package org.palmadae.donortrack.dto.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    private Long id;
    private String senderUsername;
    private String message;
    private LocalDateTime sentAt;
}