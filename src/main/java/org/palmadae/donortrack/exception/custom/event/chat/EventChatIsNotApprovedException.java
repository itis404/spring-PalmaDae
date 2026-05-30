package org.palmadae.donortrack.exception.custom.event.chat;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class EventChatIsNotApprovedException extends BusinessException {
    public EventChatIsNotApprovedException(Long eventId) {
        super("Чат мероприятия номер " + eventId + " ещё не прошёл проверку");
    }
}
