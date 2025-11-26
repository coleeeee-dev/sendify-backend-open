package com.sendify.platform.tracking.interfaces.rest.transform;

import com.sendify.platform.tracking.domain.model.commands.UpdateTrackingEventCommand;
import com.sendify.platform.tracking.interfaces.rest.resources.UpdateTrackingEventResource;

public class UpdateTrackingEventCommandFromResourceAssembler {

    public static UpdateTrackingEventCommand toCommandFromResource(
            Long trackingEventId,
            UpdateTrackingEventResource resource) {

        return new UpdateTrackingEventCommand(
                trackingEventId,
                resource.status(),
                resource.description(),
                resource.location(),
                resource.courierReference(),
                resource.timestamp()
        );
    }
}
