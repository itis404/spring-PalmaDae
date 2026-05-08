package org.palmadae.donortrack.exception.custom.email;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class EmailAlreadyExistsException extends BusinessException {
    public EmailAlreadyExistsException(String email) {
        super("Почта уже используется " + email);
    }
}