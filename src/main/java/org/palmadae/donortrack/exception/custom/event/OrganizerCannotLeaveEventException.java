package org.palmadae.donortrack.exception.custom.event;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class OrganizerCannotLeaveEventException extends BusinessException {
    public OrganizerCannotLeaveEventException() {
        super("Организатор не может покинуть своё мероприяитие");
    }
}