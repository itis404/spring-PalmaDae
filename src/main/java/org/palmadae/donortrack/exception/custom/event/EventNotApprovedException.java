package org.palmadae.donortrack.exception.custom.event;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class EventNotApprovedException extends BusinessException {
    public EventNotApprovedException(Long eventId) {
        super("Мероприятие номер " + eventId + " ещё не одобренно");
    }
}