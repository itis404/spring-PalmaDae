package org.palmadae.donortrack.exception.custom.user;

import org.palmadae.donortrack.exception.custom.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(String username) {
        super("Пользователь не найден: " + username);
    }
}