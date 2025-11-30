package com.sendify.platform.auctions.domain.model.commands;

import java.math.BigDecimal;

public record CreateAuctionRequestCommand(
        String origin,
        String destination,
        BigDecimal weightKg,
        BigDecimal lengthCm,
        BigDecimal widthCm,
        BigDecimal heightCm,
        BigDecimal declaredValue,
        String description
) {
}
