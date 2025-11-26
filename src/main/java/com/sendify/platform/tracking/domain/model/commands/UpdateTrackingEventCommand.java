package com.sendify.platform.tracking.domain.model.commands;

public record UpdateTrackingEventCommand(
        Long trackingEventId,
        String status,
        String description,
        String location,
        String courierReference,
        String timestamp // opcional
) {
}
