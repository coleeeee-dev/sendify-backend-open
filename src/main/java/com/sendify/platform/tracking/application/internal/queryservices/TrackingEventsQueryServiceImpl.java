package com.sendify.platform.tracking.application.internal.queryservices;

import com.sendify.platform.tracking.domain.model.aggregates.TrackingEvent;
import com.sendify.platform.tracking.domain.model.queries.GetAllTrackingEventsQuery;
import com.sendify.platform.tracking.domain.model.queries.GetTrackingEventByIdQuery;
import com.sendify.platform.tracking.domain.model.queries.GetTrackingEventsByShipmentIdQuery;
import com.sendify.platform.tracking.domain.services.TrackingEventsQueryService;
import com.sendify.platform.tracking.infrastructure.persistence.jpa.repositories.TrackingEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrackingEventsQueryServiceImpl implements TrackingEventsQueryService {

    private final TrackingEventRepository trackingEventRepository;

    public TrackingEventsQueryServiceImpl(TrackingEventRepository trackingEventRepository) {
        this.trackingEventRepository = trackingEventRepository;
    }

    @Override
    public Optional<TrackingEvent> handle(GetTrackingEventByIdQuery query) {
        return trackingEventRepository.findById(query.trackingEventId());
    }

    @Override
    public List<TrackingEvent> handle(GetAllTrackingEventsQuery query) {
        return trackingEventRepository.findAll();
    }

    @Override
    public List<TrackingEvent> handle(GetTrackingEventsByShipmentIdQuery query) {
        if (query.shipmentId() == null) {
            return trackingEventRepository.findAll();
        }
        return trackingEventRepository.findAllByShipmentId(query.shipmentId());
    }
}
