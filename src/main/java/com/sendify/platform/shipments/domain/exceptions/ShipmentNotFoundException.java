package com.sendify.platform.shipments.domain.exceptions;

public class ShipmentNotFoundException extends RuntimeException {
    public ShipmentNotFoundException(Long id) {
        super("Shipment with id %d not found".formatted(id));
    }
}
