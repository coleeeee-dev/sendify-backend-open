package com.sendify.platform.couriers.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class WebsiteUrl {

    @Column(name = "website_url", length = 200)
    private String value;

    public WebsiteUrl(String value) {
        if (value == null || value.isBlank()) {
            this.value = null; // website es opcional
            return;
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WebsiteUrl that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
