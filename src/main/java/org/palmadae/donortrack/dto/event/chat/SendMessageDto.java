package org.palmadae.donortrack.dto.event.chat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageDto {
    private String message;
}