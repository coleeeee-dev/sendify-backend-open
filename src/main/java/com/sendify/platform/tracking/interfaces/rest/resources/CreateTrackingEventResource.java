package com.sendify.platform.tracking.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTrackingEventResource(
        @NotNull
        Long shipmentId,

        @NotBlank
        String status,

        @NotBlank
        String description,

        String location,

        String courierReference,

        String timestamp // opcional, ISO-8601
) {
}
