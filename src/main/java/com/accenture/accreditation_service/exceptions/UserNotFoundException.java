package com.accenture.accreditation_service.exceptions;


public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(Long userId, Throwable throwable) {
        super("User with id " + userId + " not found.",throwable);
    }

    public UserNotFoundException(Long userId) {
        super("User with id " + userId + " not found.");
    }
}
