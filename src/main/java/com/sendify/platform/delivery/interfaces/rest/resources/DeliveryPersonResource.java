package com.sendify.platform.delivery.interfaces.rest.resources;

import java.util.List;

public record DeliveryPersonResource(
        Long id,
        String name,
        String code,
        String phone,
        List<Long> assignedShipments,
        boolean isActive
) {
}
