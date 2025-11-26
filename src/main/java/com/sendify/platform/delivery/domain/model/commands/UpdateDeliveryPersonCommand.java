package com.sendify.platform.delivery.domain.model.commands;

public record UpdateDeliveryPersonCommand(
        Long deliveryPersonId,
        String name,
        String phone,
        Boolean isActive
) {
}
