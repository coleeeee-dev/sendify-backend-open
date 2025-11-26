package com.sendify.platform.tracking.interfaces.rest.resources;

public record UpdateTrackingEventResource(
        String status,
        String description,
        String location,
        String courierReference,
        String timestamp // opcional
) {
}
