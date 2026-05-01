package org.palmadae.donortrack.exception.custom.event;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class OrganizerCannotLeaveEventException extends BusinessException {
    public OrganizerCannotLeaveEventException(Long eventId) {
        super("Organizer cannot leave own event: " + eventId);
    }
}