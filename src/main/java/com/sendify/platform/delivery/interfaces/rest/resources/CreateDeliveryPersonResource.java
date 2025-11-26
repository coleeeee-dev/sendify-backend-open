package com.sendify.platform.delivery.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateDeliveryPersonResource(
        @NotBlank
        String name,

        // opcional: puede ser null si queremos que se genere automáticamente
        String code,

        @NotBlank
        @Pattern(regexp = "^[+0-9 ()-]{7,20}$",
                message = "Phone must be a valid phone number")
        String phone
) {
}
