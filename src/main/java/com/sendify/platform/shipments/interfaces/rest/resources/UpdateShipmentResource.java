package com.sendify.platform.shipments.interfaces.rest.resources;

import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record UpdateShipmentResource(
        @Pattern(regexp = "^(PENDING|IN_TRANSIT|DELIVERED|CANCELLED)$")
        String status,
        String deliveryPersonId,
        LocalDate estimatedDelivery
) {
}
