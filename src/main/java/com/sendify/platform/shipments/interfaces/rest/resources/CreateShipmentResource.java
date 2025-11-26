package com.sendify.platform.shipments.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateShipmentResource(
        @Valid
        @NotNull
        ContactInformationResource sender,
        @Valid
        @NotNull
        ContactInformationResource recipient,
        @Valid
        @NotNull
        AddressResource originAddress,
        @Valid
        @NotNull
        AddressResource destinationAddress,
        @NotNull
        @Positive
        BigDecimal weight,
        @NotNull
        Long courierId,
        String deliveryPersonId
) {
}
