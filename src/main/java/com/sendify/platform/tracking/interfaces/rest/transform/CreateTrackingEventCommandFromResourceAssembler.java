package com.sendify.platform.tracking.interfaces.rest.transform;

import com.sendify.platform.tracking.domain.model.commands.CreateTrackingEventCommand;
import com.sendify.platform.tracking.interfaces.rest.resources.CreateTrackingEventResource;

public class CreateTrackingEventCommandFromResourceAssembler {

    public static CreateTrackingEventCommand toCommandFromResource(
            CreateTrackingEventResource resource) {

        return new CreateTrackingEventCommand(
                resource.shipmentId(),
                resource.status(),
                resource.description(),
                resource.location(),
                resource.courierReference(),
                resource.timestamp()
        );
    }
}
