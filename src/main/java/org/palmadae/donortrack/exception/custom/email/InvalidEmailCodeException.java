package org.palmadae.donortrack.exception.custom.email;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class InvalidEmailCodeException extends BusinessException {
    public InvalidEmailCodeException() {
        super("Invalid email code.");
    }
}
