package com.sendify.platform.auctions.domain.model.aggregates;

import com.sendify.platform.auctions.domain.model.valueobjects.AuctionRequestStatus;
import com.sendify.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "auction_requests")
public class AuctionRequest extends AuditableAbstractAggregateRoot<AuctionRequest> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin", nullable = false, length = 160)
    private String origin;

    @Column(name = "destination", nullable = false, length = 160)
    private String destination;

    @Column(name = "weight_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal weightKg;

    @Column(name = "length_cm", nullable = false, precision = 10, scale = 2)
    private BigDecimal lengthCm;

    @Column(name = "width_cm", nullable = false, precision = 10, scale = 2)
    private BigDecimal widthCm;

    @Column(name = "height_cm", nullable = false, precision = 10, scale = 2)
    private BigDecimal heightCm;

    @Column(name = "declared_value", nullable = false, precision = 10, scale = 2)
    private BigDecimal declaredValue;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AuctionRequestStatus status;

    private AuctionRequest(String origin,
                           String destination,
                           BigDecimal weightKg,
                           BigDecimal lengthCm,
                           BigDecimal widthCm,
                           BigDecimal heightCm,
                           BigDecimal declaredValue,
                           String description) {

        if (origin == null || origin.isBlank()) {
            throw new IllegalArgumentException("Origin must not be blank");
        }
        if (destination == null || destination.isBlank()) {
            throw new IllegalArgumentException("Destination must not be blank");
        }
        if (weightKg == null || weightKg.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
        if (lengthCm == null || lengthCm.compareTo(BigDecimal.ZERO) <= 0 ||
                widthCm == null || widthCm.compareTo(BigDecimal.ZERO) <= 0 ||
                heightCm == null || heightCm.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Dimensions must be greater than zero");
        }
        if (declaredValue == null || declaredValue.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Declared value must be zero or greater");
        }

        this.origin = origin.trim();
        this.destination = destination.trim();
        this.weightKg = weightKg;
        this.lengthCm = lengthCm;
        this.widthCm = widthCm;
        this.heightCm = heightCm;
        this.declaredValue = declaredValue;
        this.description = (description != null) ? description.trim() : null;
        this.status = AuctionRequestStatus.OPEN;
    }

    public static AuctionRequest create(String origin,
                                        String destination,
                                        BigDecimal weightKg,
                                        BigDecimal lengthCm,
                                        BigDecimal widthCm,
                                        BigDecimal heightCm,
                                        BigDecimal declaredValue,
                                        String description) {
        return new AuctionRequest(
                origin,
                destination,
                weightKg,
                lengthCm,
                widthCm,
                heightCm,
                declaredValue,
                description
        );
    }
}
