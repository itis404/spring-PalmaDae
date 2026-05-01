package org.palmadae.donortrack.exception.custom.email;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class EmailCodeExpiredException extends BusinessException {
    public EmailCodeExpiredException() {
        super("Verification code expired.");
    }
}