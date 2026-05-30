package org.palmadae.donortrack.exception.custom.event.chat;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class EventChatIsNotActiveException extends BusinessException {
    public EventChatIsNotActiveException(Long eventId) {
        super("Чат мероприятия " + eventId + " отключен");
    }
}
