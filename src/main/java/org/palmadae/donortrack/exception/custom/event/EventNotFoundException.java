package org.palmadae.donortrack.exception.custom.event;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class EventNotFoundException extends BusinessException {
    public EventNotFoundException(Long eventId) {
        super("Мероприятие номер " + eventId + " не найдено");
    }
}
