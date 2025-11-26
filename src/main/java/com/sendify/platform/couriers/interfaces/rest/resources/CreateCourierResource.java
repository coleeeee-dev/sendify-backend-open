package com.sendify.platform.couriers.interfaces.rest.resources;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateCourierResource(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String contactEmail,

        @NotBlank
        String contactPhone,

        String websiteUrl,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal costPerKg,

        String currency,

        @NotNull
        @Positive
        Integer estimatedDeliveryDays
) {
}
