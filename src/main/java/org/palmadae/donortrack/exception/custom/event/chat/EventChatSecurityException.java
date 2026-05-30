package org.palmadae.donortrack.exception.custom.event.chat;

public class EventChatSecurityException extends SecurityException {
    public EventChatSecurityException() {
        super("У вас нет доступа к этому чату");
    }
}
