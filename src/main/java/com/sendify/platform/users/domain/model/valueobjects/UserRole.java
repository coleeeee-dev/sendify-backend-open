package com.sendify.platform.users.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Embeddable
@NoArgsConstructor
public class UserRole {

    private static final Set<String> ALLOWED_ROLES = Set.of(
            "ADMIN", "OPERATOR", "VIEWER", "ANALYST"
    );

    private static final Map<String, String> NORMALIZATION = Map.of(
            "admin", "ADMIN",
            "ADMIN", "ADMIN",
            "operator", "OPERATOR",
            "OPERATOR", "OPERATOR",
            "viewer", "VIEWER",
            "VIEWER", "VIEWER",
            "analyst", "ANALYST",
            "ANALYST", "ANALYST"
    );

    @Column(name = "role", nullable = false, length = 30)
    private String value;

    public UserRole(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Role must not be blank");
        }
        String normalized = NORMALIZATION.getOrDefault(value.trim(), value.trim().toUpperCase());
        if (!ALLOWED_ROLES.contains(normalized)) {
            throw new IllegalArgumentException("Invalid role: " + value);
        }
        this.value = normalized;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole userRole)) return false;
        return Objects.equals(value, userRole.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
