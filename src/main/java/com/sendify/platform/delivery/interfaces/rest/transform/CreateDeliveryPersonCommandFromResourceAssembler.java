package com.sendify.platform.delivery.interfaces.rest.transform;

import com.sendify.platform.delivery.domain.model.commands.CreateDeliveryPersonCommand;
import com.sendify.platform.delivery.interfaces.rest.resources.CreateDeliveryPersonResource;

public class CreateDeliveryPersonCommandFromResourceAssembler {

    public static CreateDeliveryPersonCommand toCommandFromResource(
            CreateDeliveryPersonResource resource) {

        return new CreateDeliveryPersonCommand(
                resource.name(),
                resource.code(),
                resource.phone()
        );
    }
}
