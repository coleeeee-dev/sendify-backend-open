package com.sendify.platform.tracking.domain.exceptions;

public class TrackingEventNotFoundException extends RuntimeException {
    public TrackingEventNotFoundException(Long id) {
        super("Tracking event with id %d not found".formatted(id));
    }
}
