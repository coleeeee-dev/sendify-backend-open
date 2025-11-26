package com.sendify.platform.shipments.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ShipmentResource(
        Long id,
        String trackingCode,
        ContactInformationResource sender,
        ContactInformationResource recipient,
        AddressResource originAddress,
        AddressResource destinationAddress,
        BigDecimal weight,
        BigDecimal cost,
        String status,
        Long courierId,
        String deliveryPersonId,
        LocalDate estimatedDelivery,
        Instant createdAt,
        Instant updatedAt
) {
}
