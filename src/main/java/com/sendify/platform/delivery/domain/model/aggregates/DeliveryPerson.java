package com.sendify.platform.delivery.domain.model.aggregates;

import com.sendify.platform.delivery.domain.model.valueobjects.DeliveryPersonCode;
import com.sendify.platform.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.sendify.platform.shared.domain.model.valueobjects.PhoneNumber;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "delivery_persons")
public class DeliveryPerson extends AuditableAbstractAggregateRoot<DeliveryPerson> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 140)
    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "code", nullable = false, length = 20, unique = true))
    private DeliveryPersonCode code;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "phone", nullable = false, length = 40))
    private PhoneNumber phone;

    @ElementCollection
    @CollectionTable(
            name = "delivery_person_assigned_shipments",
            joinColumns = @JoinColumn(name = "delivery_person_id")
    )
    @Column(name = "shipment_id", nullable = false)
    private List<Long> assignedShipments = new ArrayList<>();

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    private DeliveryPerson(String name,
                           DeliveryPersonCode code,
                           PhoneNumber phone) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }

        this.name = name.trim();
        this.code = code;
        this.phone = phone;
        this.assignedShipments = new ArrayList<>();
        this.isActive = true;
    }

    public static DeliveryPerson create(String name,
                                        DeliveryPersonCode code,
                                        PhoneNumber phone) {
        return new DeliveryPerson(name, code, phone);
    }

    public void update(String name,
                       PhoneNumber phone,
                       Boolean isActive) {

        if (name != null && !name.isBlank()) {
            this.name = name.trim();
        }

        if (phone != null) {
            this.phone = phone;
        }

        if (isActive != null) {
            this.isActive = isActive;
        }
    }

    public void assignShipment(Long shipmentId) {
        if (shipmentId != null && !assignedShipments.contains(shipmentId)) {
            assignedShipments.add(shipmentId);
        }
    }
}
