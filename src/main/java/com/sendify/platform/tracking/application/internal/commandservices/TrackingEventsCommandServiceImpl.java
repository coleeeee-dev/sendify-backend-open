package com.sendify.platform.tracking.application.internal.commandservices;

import com.sendify.platform.tracking.domain.exceptions.TrackingEventNotFoundException;
import com.sendify.platform.tracking.domain.model.aggregates.TrackingEvent;
import com.sendify.platform.tracking.domain.model.commands.CreateTrackingEventCommand;
import com.sendify.platform.tracking.domain.model.commands.UpdateTrackingEventCommand;
import com.sendify.platform.tracking.domain.model.valueobjects.TrackingStatus;
import com.sendify.platform.tracking.domain.services.TrackingEventsCommandService;
import com.sendify.platform.tracking.infrastructure.persistence.jpa.repositories.TrackingEventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;

@Service
@Transactional
public class TrackingEventsCommandServiceImpl implements TrackingEventsCommandService {

    private final TrackingEventRepository trackingEventRepository;

    public TrackingEventsCommandServiceImpl(TrackingEventRepository trackingEventRepository) {
        this.trackingEventRepository = trackingEventRepository;
    }

    @Override
    public TrackingEvent handle(CreateTrackingEventCommand command) {

        Instant timestamp = parseTimestampOrNow(command.timestamp());

        var trackingEvent = TrackingEvent.create(
                command.shipmentId(),
                new TrackingStatus(command.status()),
                command.description(),
                command.location(),
                timestamp,
                command.courierReference()
        );

        return trackingEventRepository.save(trackingEvent);
    }

    @Override
    public TrackingEvent handle(UpdateTrackingEventCommand command) {

        var trackingEvent = trackingEventRepository.findById(command.trackingEventId())
                .orElseThrow(() -> new TrackingEventNotFoundException(command.trackingEventId()));

        Instant timestamp = parseTimestampOrNull(command.timestamp());

        trackingEvent.update(
                command.status(),
                command.description(),
                command.location(),
                timestamp,
                command.courierReference()
        );

        return trackingEventRepository.save(trackingEvent);
    }

    @Override
    public void delete(Long trackingEventId) {
        if (!trackingEventRepository.existsById(trackingEventId)) {
            throw new TrackingEventNotFoundException(trackingEventId);
        }
        trackingEventRepository.deleteById(trackingEventId);
    }

    private Instant parseTimestampOrNow(String timestamp) {
        if (timestamp == null || timestamp.isBlank()) {
            return Instant.now();
        }
        try {
            return Instant.parse(timestamp);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid timestamp format, must be ISO-8601", e);
        }
    }

    private Instant parseTimestampOrNull(String timestamp) {
        if (timestamp == null || timestamp.isBlank()) return null;
        try {
            return Instant.parse(timestamp);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid timestamp format, must be ISO-8601", e);
        }
    }
}
