package com.sendify.platform.auctions.interfaces.rest.resources;

import java.math.BigDecimal;
import java.time.Instant;

public record AuctionRequestResource(
        Long id,
        String origin,
        String destination,
        BigDecimal weightKg,
        BigDecimal lengthCm,
        BigDecimal widthCm,
        BigDecimal heightCm,
        BigDecimal declaredValue,
        String description,
        String status,
        Instant createdAt
) {
}
