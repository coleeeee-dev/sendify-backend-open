package com.sendify.platform.delivery.domain.exceptions;

public class DeliveryPersonNotFoundException extends RuntimeException {
    public DeliveryPersonNotFoundException(Long id) {
        super("Delivery person with id %d not found".formatted(id));
    }
}
