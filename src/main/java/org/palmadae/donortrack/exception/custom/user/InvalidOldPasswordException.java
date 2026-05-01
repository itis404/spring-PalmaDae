package org.palmadae.donortrack.exception.custom.user;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class InvalidOldPasswordException extends BusinessException {
    public InvalidOldPasswordException() {
        super("Old password is incorrect");
    }
}