package com.sendify.platform.shipments.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record AddressResource(
        @NotBlank
        String street,
        @NotBlank
        String city,
        @NotBlank
        String state,
        @NotBlank
        String zipCode,
        @NotBlank
        String country
) {
}
