package com.sendify.platform.shipments.domain.model.valueobjects;

import java.security.SecureRandom;
import java.util.Locale;

public final class TrackingCode {

    private static final String PREFIX = "SFY";
    private static final SecureRandom RANDOM = new SecureRandom();

    private TrackingCode() {
    }

    public static String generate() {
        // SFY + 9 dígitos
        StringBuilder sb = new StringBuilder(PREFIX);
        for (int i = 0; i < 9; i++) {
            int digit = RANDOM.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    public static void validate(String trackingCode) {
        if (trackingCode == null || trackingCode.isBlank()) {
            throw new IllegalArgumentException("Tracking code must not be blank");
        }

        String code = trackingCode.toUpperCase(Locale.ROOT).trim();
        if (!code.matches("^SFY\\d{9}$")) {
            throw new IllegalArgumentException("Tracking code must match pattern SFY#########");
        }
    }
}
