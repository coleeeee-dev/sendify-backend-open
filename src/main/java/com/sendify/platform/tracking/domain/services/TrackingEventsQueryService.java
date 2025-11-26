package com.sendify.platform.tracking.domain.services;

import com.sendify.platform.tracking.domain.model.aggregates.TrackingEvent;
import com.sendify.platform.tracking.domain.model.queries.GetAllTrackingEventsQuery;
import com.sendify.platform.tracking.domain.model.queries.GetTrackingEventByIdQuery;
import com.sendify.platform.tracking.domain.model.queries.GetTrackingEventsByShipmentIdQuery;

import java.util.List;
import java.util.Optional;

public interface TrackingEventsQueryService {

    Optional<TrackingEvent> handle(GetTrackingEventByIdQuery query);

    List<TrackingEvent> handle(GetAllTrackingEventsQuery query);

    List<TrackingEvent> handle(GetTrackingEventsByShipmentIdQuery query);
}
