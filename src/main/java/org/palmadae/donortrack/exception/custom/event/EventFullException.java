package org.palmadae.donortrack.exception.custom.event;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class EventFullException extends BusinessException {
    public EventFullException(Long eventId) {
        super("Мероприятие нормер " + eventId + " уже заполненно");
    }
}