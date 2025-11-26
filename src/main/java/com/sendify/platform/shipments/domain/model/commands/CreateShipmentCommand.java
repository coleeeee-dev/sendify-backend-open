package com.sendify.platform.shipments.domain.model.commands;

import java.math.BigDecimal;

public record CreateShipmentCommand(
        String senderName,
        String senderEmail,
        String senderPhone,
        String recipientName,
        String recipientEmail,
        String recipientPhone,
        String originStreet,
        String originCity,
        String originState,
        String originZipCode,
        String originCountry,
        String destinationStreet,
        String destinationCity,
        String destinationState,
        String destinationZipCode,
        String destinationCountry,
        BigDecimal weight,
        Long courierId,
        String deliveryPersonId
) {
}
