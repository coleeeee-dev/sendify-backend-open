package com.sendify.platform.auctions.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateAuctionRequestResource(
        @NotBlank
        String origin,

        @NotBlank
        String destination,

        @NotNull
        @Positive
        BigDecimal weightKg,

        @NotNull
        @Positive
        BigDecimal lengthCm,

        @NotNull
        @Positive
        BigDecimal widthCm,

        @NotNull
        @Positive
        BigDecimal heightCm,

        @NotNull
        BigDecimal declaredValue,

        @Size(max = 500)
        String description
) {
}
