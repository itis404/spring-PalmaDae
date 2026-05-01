package org.palmadae.donortrack.exception.custom.event;

public class EventSecurityException extends SecurityException {
    public EventSecurityException() {
        super("Нет доступа к настройкам мероприятия");
    }
}
