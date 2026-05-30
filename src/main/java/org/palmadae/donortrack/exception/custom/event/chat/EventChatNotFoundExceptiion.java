package org.palmadae.donortrack.exception.custom.event.chat;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class EventChatNotFoundExceptiion extends BusinessException {
    public EventChatNotFoundExceptiion(Long eventId) {
        super("Чат мероприятия номер " + eventId + " не найден");
    }
}
