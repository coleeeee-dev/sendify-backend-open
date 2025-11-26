package com.sendify.platform.shipments.domain.model.commands;

import java.time.LocalDate;

public record UpdateShipmentStatusCommand(
        Long shipmentId,
        String status,
        String deliveryPersonId,
        LocalDate estimatedDelivery
) {
}
