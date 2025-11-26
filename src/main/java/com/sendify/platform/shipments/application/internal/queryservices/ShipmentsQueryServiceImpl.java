package com.sendify.platform.shipments.application.internal.queryservices;

import com.sendify.platform.shipments.domain.model.aggregates.Shipment;
import com.sendify.platform.shipments.domain.model.queries.GetShipmentByIdQuery;
import com.sendify.platform.shipments.domain.model.queries.GetShipmentsQuery;
import com.sendify.platform.shipments.domain.model.valueobjects.ShipmentStatus;
import com.sendify.platform.shipments.domain.services.ShipmentsQueryService;
import com.sendify.platform.shipments.infrastructure.persistence.jpa.repositories.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ShipmentsQueryServiceImpl implements ShipmentsQueryService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentsQueryServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Optional<Shipment> handle(GetShipmentByIdQuery query) {
        return shipmentRepository.findById(query.shipmentId());
    }

    @Override
    public List<Shipment> handle(GetShipmentsQuery query) {
        String trackingCode = query.trackingCode();
        String statusStr = query.status();
        String deliveryPersonId = query.deliveryPersonId();

        if (trackingCode != null && !trackingCode.isBlank()) {
            return shipmentRepository.findByTrackingCode(trackingCode)
                    .map(List::of)
                    .orElseGet(List::of);
        }

        ShipmentStatus status = null;
        if (statusStr != null && !statusStr.isBlank()) {
            status = ShipmentStatus.valueOf(statusStr.toUpperCase());
        }

        if (status != null && deliveryPersonId != null && !deliveryPersonId.isBlank()) {
            return shipmentRepository.findByStatusAndDeliveryPersonId(status, deliveryPersonId);
        }

        if (status != null) {
            return shipmentRepository.findByStatus(status);
        }

        if (deliveryPersonId != null && !deliveryPersonId.isBlank()) {
            return shipmentRepository.findByDeliveryPersonId(deliveryPersonId);
        }

        return shipmentRepository.findAll();
    }
}
