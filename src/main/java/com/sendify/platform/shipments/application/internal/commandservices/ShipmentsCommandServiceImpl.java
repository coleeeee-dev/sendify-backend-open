package com.sendify.platform.shipments.application.internal.commandservices;

import com.sendify.platform.shipments.domain.exceptions.ShipmentNotFoundException;
import com.sendify.platform.shipments.domain.model.aggregates.Shipment;
import com.sendify.platform.shipments.domain.model.commands.CreateShipmentCommand;
import com.sendify.platform.shipments.domain.model.commands.DeleteShipmentCommand;
import com.sendify.platform.shipments.domain.model.commands.UpdateShipmentStatusCommand;
import com.sendify.platform.shipments.domain.model.valueobjects.Address;
import com.sendify.platform.shipments.domain.model.valueobjects.ShipmentStatus;
import com.sendify.platform.shipments.domain.model.valueobjects.TrackingCode;
import com.sendify.platform.shipments.domain.services.ShipmentsCommandService;
import com.sendify.platform.shipments.infrastructure.persistence.jpa.repositories.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
public class ShipmentsCommandServiceImpl implements ShipmentsCommandService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentsCommandServiceImpl(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Shipment handle(CreateShipmentCommand command) {
        String trackingCode = TrackingCode.generate();

        Address originAddress = new Address(
                command.originStreet(),
                command.originCity(),
                command.originState(),
                command.originZipCode(),
                command.originCountry()
        );

        Address destinationAddress = new Address(
                command.destinationStreet(),
                command.destinationCity(),
                command.destinationState(),
                command.destinationZipCode(),
                command.destinationCountry()
        );

        Shipment shipment = Shipment.create(
                trackingCode,
                command.senderName(),
                command.senderEmail(),
                command.senderPhone(),
                command.recipientName(),
                command.recipientEmail(),
                command.recipientPhone(),
                originAddress,
                destinationAddress,
                command.weight(),
                command.courierId(),
                command.deliveryPersonId()
        );

        return shipmentRepository.save(shipment);
    }

    @Override
    public Shipment handle(UpdateShipmentStatusCommand command) {
        Shipment shipment = shipmentRepository.findById(command.shipmentId())
                .orElseThrow(() -> new ShipmentNotFoundException(command.shipmentId()));

        ShipmentStatus status = null;
        if (command.status() != null) {
            status = ShipmentStatus.valueOf(command.status().toUpperCase());
        }

        LocalDate estimatedDelivery = command.estimatedDelivery();

        shipment.updateStatus(status, command.deliveryPersonId(), estimatedDelivery);

        return shipmentRepository.save(shipment);
    }

    @Override
    public void handle(DeleteShipmentCommand command) {
        if (!shipmentRepository.existsById(command.shipmentId())) {
            throw new ShipmentNotFoundException(command.shipmentId());
        }
        shipmentRepository.deleteById(command.shipmentId());
    }
}
