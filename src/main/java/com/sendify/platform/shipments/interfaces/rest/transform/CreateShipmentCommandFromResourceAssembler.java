package com.sendify.platform.shipments.interfaces.rest.transform;

import com.sendify.platform.shipments.domain.model.commands.CreateShipmentCommand;
import com.sendify.platform.shipments.interfaces.rest.resources.CreateShipmentResource;

public class CreateShipmentCommandFromResourceAssembler {

    public static CreateShipmentCommand toCommandFromResource(CreateShipmentResource resource) {

        var sender = resource.sender();
        var recipient = resource.recipient();
        var origin = resource.originAddress();
        var destination = resource.destinationAddress();

        return new CreateShipmentCommand(
                sender.name(),
                sender.email(),
                sender.phone(),
                recipient.name(),
                recipient.email(),
                recipient.phone(),
                origin.street(),
                origin.city(),
                origin.state(),
                origin.zipCode(),
                origin.country(),
                destination.street(),
                destination.city(),
                destination.state(),
                destination.zipCode(),
                destination.country(),
                resource.weight(),
                resource.courierId(),
                resource.deliveryPersonId()
        );
    }
}
