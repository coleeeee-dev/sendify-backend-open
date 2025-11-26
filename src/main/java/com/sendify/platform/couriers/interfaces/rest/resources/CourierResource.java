package com.sendify.platform.couriers.interfaces.rest.resources;

import java.math.BigDecimal;

public record CourierResource(
        Long id,
        String name,
        String contactEmail,
        String contactPhone,
        String websiteUrl,
        BigDecimal costPerKg,
        String currency,
        Integer estimatedDeliveryDays
) {
}
