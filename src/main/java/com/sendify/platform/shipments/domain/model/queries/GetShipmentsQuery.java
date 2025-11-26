package com.sendify.platform.shipments.domain.model.queries;

public record GetShipmentsQuery(
        String status,
        String trackingCode,
        String deliveryPersonId
) {
}
