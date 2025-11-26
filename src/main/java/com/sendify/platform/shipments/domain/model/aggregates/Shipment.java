package com.sendify.platform.shipments.domain.model.aggregates;

import com.sendify.platform.shipments.domain.model.valueobjects.Address;
import com.sendify.platform.shipments.domain.model.valueobjects.ShipmentStatus;
import com.sendify.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "shipments")
public class Shipment extends AuditableAbstractAggregateRoot<Shipment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_code", nullable = false, unique = true, length = 20)
    private String trackingCode;

    // Sender
    @Column(name = "sender_name", nullable = false, length = 120)
    private String senderName;

    @Column(name = "sender_email", nullable = false, length = 160)
    private String senderEmail;

    @Column(name = "sender_phone", nullable = false, length = 40)
    private String senderPhone;

    // Recipient
    @Column(name = "recipient_name", nullable = false, length = 120)
    private String recipientName;

    @Column(name = "recipient_email", nullable = false, length = 160)
    private String recipientEmail;

    @Column(name = "recipient_phone", nullable = false, length = 40)
    private String recipientPhone;

    // Addresses
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "origin_street")),
            @AttributeOverride(name = "city", column = @Column(name = "origin_city")),
            @AttributeOverride(name = "state", column = @Column(name = "origin_state")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "origin_zip_code")),
            @AttributeOverride(name = "country", column = @Column(name = "origin_country"))
    })
    private Address originAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "destination_street")),
            @AttributeOverride(name = "city", column = @Column(name = "destination_city")),
            @AttributeOverride(name = "state", column = @Column(name = "destination_state")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "destination_zip_code")),
            @AttributeOverride(name = "country", column = @Column(name = "destination_country"))
    })
    private Address destinationAddress;

    @Column(name = "weight", nullable = false, precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(name = "cost", nullable = false, precision = 12, scale = 2)
    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ShipmentStatus status;

    @Column(name = "courier_id", nullable = false)
    private Long courierId;

    @Column(name = "delivery_person_id", length = 50)
    private String deliveryPersonId;

    @Column(name = "estimated_delivery")
    private LocalDate estimatedDelivery;

    private Shipment(String trackingCode,
                     String senderName,
                     String senderEmail,
                     String senderPhone,
                     String recipientName,
                     String recipientEmail,
                     String recipientPhone,
                     Address originAddress,
                     Address destinationAddress,
                     BigDecimal weight,
                     BigDecimal cost,
                     ShipmentStatus status,
                     Long courierId,
                     String deliveryPersonId,
                     LocalDate estimatedDelivery) {

        if (senderName == null || senderName.isBlank())
            throw new IllegalArgumentException("Sender name must not be blank");
        if (recipientName == null || recipientName.isBlank())
            throw new IllegalArgumentException("Recipient name must not be blank");
        if (weight == null || weight.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Weight must be greater than zero");
        if (courierId == null)
            throw new IllegalArgumentException("Courier id is required");

        this.trackingCode = trackingCode;
        this.senderName = senderName.trim();
        this.senderEmail = senderEmail.trim();
        this.senderPhone = senderPhone.trim();
        this.recipientName = recipientName.trim();
        this.recipientEmail = recipientEmail.trim();
        this.recipientPhone = recipientPhone.trim();
        this.originAddress = originAddress;
        this.destinationAddress = destinationAddress;
        this.weight = weight;
        this.cost = cost;
        this.status = status;
        this.courierId = courierId;
        this.deliveryPersonId = deliveryPersonId;
        this.estimatedDelivery = estimatedDelivery;
    }

    public static Shipment create(String trackingCode,
                                  String senderName,
                                  String senderEmail,
                                  String senderPhone,
                                  String recipientName,
                                  String recipientEmail,
                                  String recipientPhone,
                                  Address originAddress,
                                  Address destinationAddress,
                                  BigDecimal weight,
                                  Long courierId,
                                  String deliveryPersonId) {

        BigDecimal initialCost = BigDecimal.ZERO; // según enunciado, arranca en 0
        ShipmentStatus initialStatus = ShipmentStatus.PENDING;

        return new Shipment(
                trackingCode,
                senderName,
                senderEmail,
                senderPhone,
                recipientName,
                recipientEmail,
                recipientPhone,
                originAddress,
                destinationAddress,
                weight,
                initialCost,
                initialStatus,
                courierId,
                deliveryPersonId,
                null
        );
    }

    public void updateStatus(ShipmentStatus status,
                             String deliveryPersonId,
                             LocalDate estimatedDelivery) {

        if (status != null) {
            this.status = status;
        }
        if (deliveryPersonId != null && !deliveryPersonId.isBlank()) {
            this.deliveryPersonId = deliveryPersonId.trim();
        }
        if (estimatedDelivery != null) {
            this.estimatedDelivery = estimatedDelivery;
        }
    }
}
