package com.sendify.platform.couriers.domain.model.commands;

import java.math.BigDecimal;

public record UpdateCourierCommand(
        Long courierId,
        String name,
        String contactEmail,
        String contactPhone,
        String websiteUrl,
        BigDecimal costPerKg,
        String currency,
        Integer estimatedDeliveryDays
) {
}
