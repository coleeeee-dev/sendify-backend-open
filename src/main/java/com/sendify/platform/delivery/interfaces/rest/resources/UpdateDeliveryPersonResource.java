package com.sendify.platform.delivery.interfaces.rest.resources;

import jakarta.validation.constraints.Pattern;

public record UpdateDeliveryPersonResource(
        String name,

        @Pattern(regexp = "^[+0-9 ()-]{7,20}$",
                message = "Phone must be a valid phone number")
        String phone,

        Boolean isActive
) {
}
