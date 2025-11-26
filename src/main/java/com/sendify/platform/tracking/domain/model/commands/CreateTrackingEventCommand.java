package com.sendify.platform.tracking.domain.model.commands;

public record CreateTrackingEventCommand(
        Long shipmentId,
        String status,
        String description,
        String location,
        String courierReference,
        String timestamp // opcional ISO-8601, si viene la parseamos
) {
}
