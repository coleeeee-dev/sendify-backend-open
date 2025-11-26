package com.sendify.platform.tracking.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Embeddable
@NoArgsConstructor
public class TrackingStatus {

    private static final Set<String> ALLOWED_STATUSES = Set.of(
            "REGISTERED",
            "IN_TRANSIT",
            "DELIVERED",
            "DELAYED",
            "CANCELLED"
    );

    @Column(name = "status", nullable = false, length = 30)
    private String value;

    public TrackingStatus(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Tracking status must not be blank");
        }
        String normalized = value.trim().toUpperCase();
        if (!ALLOWED_STATUSES.contains(normalized)) {
            throw new IllegalArgumentException("Invalid tracking status: " + value);
        }
        this.value = normalized;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackingStatus that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
