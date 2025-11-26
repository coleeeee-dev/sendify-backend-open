package com.sendify.platform.shipments.infrastructure.persistence.jpa.repositories;

import com.sendify.platform.shipments.domain.model.aggregates.Shipment;
import com.sendify.platform.shipments.domain.model.valueobjects.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByTrackingCode(String trackingCode);

    List<Shipment> findByStatus(ShipmentStatus status);

    List<Shipment> findByDeliveryPersonId(String deliveryPersonId);

    List<Shipment> findByStatusAndDeliveryPersonId(ShipmentStatus status, String deliveryPersonId);
}
