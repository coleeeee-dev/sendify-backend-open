package com.sendify.platform.tracking.interfaces.rest.transform;

import com.sendify.platform.tracking.domain.model.aggregates.TrackingEvent;
import com.sendify.platform.tracking.interfaces.rest.resources.TrackingEventResource;

public class TrackingEventResourceFromEntityAssembler {

    public static TrackingEventResource toResourceFromEntity(TrackingEvent event) {
        String timestamp = event.getTimestamp() != null
                ? event.getTimestamp().toString()
                : null;

        return new TrackingEventResource(
                event.getId(),
                event.getShipmentId(),
                event.getStatus().getValue(),
                event.getDescription(),
                event.getLocation(),
                timestamp,
                event.getCourierReference()
        );
    }
}
