package com.sendify.platform.couriers.domain.exceptions;

public class CourierNotFoundException extends RuntimeException {
    public CourierNotFoundException(Long id) {
        super("Courier with id %d not found".formatted(id));
    }
}
