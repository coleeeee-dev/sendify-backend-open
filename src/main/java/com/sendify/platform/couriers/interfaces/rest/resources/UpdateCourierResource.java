package com.sendify.platform.couriers.interfaces.rest.resources;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record UpdateCourierResource(
        String name,

        @Email
        String contactEmail,

        String contactPhone,

        String websiteUrl,

        @DecimalMin(value = "0.01")
        BigDecimal costPerKg,

        String currency,

        @Positive
        Integer estimatedDeliveryDays
) {
}
