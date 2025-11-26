package com.sendify.platform.delivery.interfaces.rest.transform;

import com.sendify.platform.delivery.domain.model.commands.UpdateDeliveryPersonCommand;
import com.sendify.platform.delivery.interfaces.rest.resources.UpdateDeliveryPersonResource;

public class UpdateDeliveryPersonCommandFromResourceAssembler {

    public static UpdateDeliveryPersonCommand toCommandFromResource(
            Long deliveryPersonId,
            UpdateDeliveryPersonResource resource) {

        return new UpdateDeliveryPersonCommand(
                deliveryPersonId,
                resource.name(),
                resource.phone(),
                resource.isActive()
        );
    }
}
