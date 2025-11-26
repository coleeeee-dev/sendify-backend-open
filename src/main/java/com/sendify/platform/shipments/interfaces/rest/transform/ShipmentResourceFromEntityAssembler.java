package com.sendify.platform.shipments.interfaces.rest.transform;

import com.sendify.platform.shipments.domain.model.aggregates.Shipment;
import com.sendify.platform.shipments.interfaces.rest.resources.*;

public class ShipmentResourceFromEntityAssembler {

    public static ShipmentResource toResourceFromEntity(Shipment shipment) {

        ContactInformationResource sender = new ContactInformationResource(
                shipment.getSenderName(),
                shipment.getSenderEmail(),
                shipment.getSenderPhone()
        );

        ContactInformationResource recipient = new ContactInformationResource(
                shipment.getRecipientName(),
                shipment.getRecipientEmail(),
                shipment.getRecipientPhone()
        );

        AddressResource origin = new AddressResource(
                shipment.getOriginAddress().getStreet(),
                shipment.getOriginAddress().getCity(),
                shipment.getOriginAddress().getState(),
                shipment.getOriginAddress().getZipCode(),
                shipment.getOriginAddress().getCountry()
        );

        AddressResource destination = new AddressResource(
                shipment.getDestinationAddress().getStreet(),
                shipment.getDestinationAddress().getCity(),
                shipment.getDestinationAddress().getState(),
                shipment.getDestinationAddress().getZipCode(),
                shipment.getDestinationAddress().getCountry()
        );

        return new ShipmentResource(
                shipment.getId(),
                shipment.getTrackingCode(),
                sender,
                recipient,
                origin,
                destination,
                shipment.getWeight(),
                shipment.getCost(),
                shipment.getStatus().name(),
                shipment.getCourierId(),
                shipment.getDeliveryPersonId(),
                shipment.getEstimatedDelivery(),
                shipment.getCreatedAt(),
                shipment.getUpdatedAt()
        );
    }
}
