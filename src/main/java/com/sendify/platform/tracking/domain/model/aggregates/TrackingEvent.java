package com.sendify.platform.tracking.domain.model.aggregates;

import com.sendify.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.sendify.platform.tracking.domain.model.valueobjects.TrackingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tracking_events")
public class TrackingEvent extends AuditableAbstractAggregateRoot<TrackingEvent> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shipment_id", nullable = false)
    private Long shipmentId;

    @Embedded
    private TrackingStatus status;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "location", length = 140)
    private String location;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "courier_reference", length = 60)
    private String courierReference;

    private TrackingEvent(Long shipmentId,
                          TrackingStatus status,
                          String description,
                          String location,
                          Instant timestamp,
                          String courierReference) {

        if (shipmentId == null) {
            throw new IllegalArgumentException("Shipment id is required");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description must not be blank");
        }

        this.shipmentId = shipmentId;
        this.status = status;
        this.description = description.trim();
        this.location = location != null ? location.trim() : null;
        this.timestamp = timestamp != null ? timestamp : Instant.now();
        this.courierReference = (courierReference != null && !courierReference.isBlank())
                ? courierReference.trim()
                : null;
    }

    public static TrackingEvent create(Long shipmentId,
                                       TrackingStatus status,
                                       String description,
                                       String location,
                                       Instant timestamp,
                                       String courierReference) {

        return new TrackingEvent(
                shipmentId,
                status,
                description,
                location,
                timestamp,
                courierReference
        );
    }

    public void update(String status,
                       String description,
                       String location,
                       Instant timestamp,
                       String courierReference) {

        if (status != null && !status.isBlank()) {
            this.status = new TrackingStatus(status);
        }
        if (description != null && !description.isBlank()) {
            this.description = description.trim();
        }
        if (location != null) {
            this.location = location.trim();
        }
        if (timestamp != null) {
            this.timestamp = timestamp;
        }
        if (courierReference != null) {
            this.courierReference = courierReference.isBlank()
                    ? null
                    : courierReference.trim();
        }
    }
}
