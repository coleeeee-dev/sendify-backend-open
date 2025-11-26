package com.sendify.platform.shipments.interfaces.rest.transform;

import com.sendify.platform.shipments.domain.model.commands.UpdateShipmentStatusCommand;
import com.sendify.platform.shipments.interfaces.rest.resources.UpdateShipmentResource;

public class UpdateShipmentStatusCommandFromResourceAssembler {

    public static UpdateShipmentStatusCommand toCommandFromResource(
            Long shipmentId,
            UpdateShipmentResource resource) {

        return new UpdateShipmentStatusCommand(
                shipmentId,
                resource.status(),
                resource.deliveryPersonId(),
                resource.estimatedDelivery()
        );
    }
}
