package com.sendify.platform.delivery.domain.model.commands;

public record CreateDeliveryPersonCommand(
        String name,
        String code,
        String phone
) {
}
