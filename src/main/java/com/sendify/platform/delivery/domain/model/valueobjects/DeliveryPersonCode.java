package com.sendify.platform.delivery.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Random;

@Embeddable
@NoArgsConstructor
public class DeliveryPersonCode {

    @Column(name = "code", length = 20, unique = true, nullable = false)
    private String value;

    public DeliveryPersonCode(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Delivery person code must not be blank");
        }
        this.value = value.trim().toUpperCase();
    }

    public String getValue() {
        return value;
    }

    // Generador simple tipo DEL001, DEL002, etc.
    public static String generateRandom() {
        var random = new Random();
        int number = 1 + random.nextInt(999_999);
        return "DEL%06d".formatted(number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeliveryPersonCode that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
