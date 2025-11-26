package com.sendify.platform.tracking.domain.services;

import com.sendify.platform.tracking.domain.model.aggregates.TrackingEvent;
import com.sendify.platform.tracking.domain.model.commands.CreateTrackingEventCommand;
import com.sendify.platform.tracking.domain.model.commands.UpdateTrackingEventCommand;

public interface TrackingEventsCommandService {

    TrackingEvent handle(CreateTrackingEventCommand command);

    TrackingEvent handle(UpdateTrackingEventCommand command);

    void delete(Long trackingEventId);
}
