package com.sendify.platform.users.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with id %d not found".formatted(id));
    }
}
