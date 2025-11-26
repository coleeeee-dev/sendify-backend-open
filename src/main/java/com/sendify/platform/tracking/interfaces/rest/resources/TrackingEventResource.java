package com.sendify.platform.tracking.interfaces.rest.resources;

public record TrackingEventResource(
        Long id,
        Long shipmentId,
        String status,
        String description,
        String location,
        String timestamp,
        String courierReference
) {
}
