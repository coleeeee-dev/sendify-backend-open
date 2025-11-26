package com.sendify.platform.tracking.interfaces.rest;

import com.sendify.platform.tracking.domain.exceptions.TrackingEventNotFoundException;
import com.sendify.platform.tracking.domain.model.aggregates.TrackingEvent;
import com.sendify.platform.tracking.domain.model.commands.CreateTrackingEventCommand;
import com.sendify.platform.tracking.domain.model.commands.UpdateTrackingEventCommand;
import com.sendify.platform.tracking.domain.model.queries.GetAllTrackingEventsQuery;
import com.sendify.platform.tracking.domain.model.queries.GetTrackingEventByIdQuery;
import com.sendify.platform.tracking.domain.model.queries.GetTrackingEventsByShipmentIdQuery;
import com.sendify.platform.tracking.domain.services.TrackingEventsCommandService;
import com.sendify.platform.tracking.domain.services.TrackingEventsQueryService;
import com.sendify.platform.tracking.interfaces.rest.resources.CreateTrackingEventResource;
import com.sendify.platform.tracking.interfaces.rest.resources.TrackingEventResource;
import com.sendify.platform.tracking.interfaces.rest.resources.UpdateTrackingEventResource;
import com.sendify.platform.tracking.interfaces.rest.transform.CreateTrackingEventCommandFromResourceAssembler;
import com.sendify.platform.tracking.interfaces.rest.transform.TrackingEventResourceFromEntityAssembler;
import com.sendify.platform.tracking.interfaces.rest.transform.UpdateTrackingEventCommandFromResourceAssembler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/trackingEvents")
public class TrackingEventsController {

    private final TrackingEventsCommandService commandService;
    private final TrackingEventsQueryService queryService;

    public TrackingEventsController(TrackingEventsCommandService commandService,
                                    TrackingEventsQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    // GET /trackingEvents?shipmentId=XXX
    @GetMapping
    public ResponseEntity<List<TrackingEventResource>> getTrackingEvents(
            @RequestParam(required = false) Long shipmentId) {

        List<TrackingEvent> events;

        if (shipmentId != null) {
            var query = new GetTrackingEventsByShipmentIdQuery(shipmentId);
            events = queryService.handle(query);
        } else {
            var query = new GetAllTrackingEventsQuery();
            events = queryService.handle(query);
        }

        List<TrackingEventResource> resources = events.stream()
                .map(TrackingEventResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    // GET /trackingEvents/{id}
    @GetMapping("{trackingEventId}")
    public ResponseEntity<TrackingEventResource> getTrackingEventById(
            @PathVariable Long trackingEventId) {

        var query = new GetTrackingEventByIdQuery(trackingEventId);

        TrackingEvent event = queryService.handle(query)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Tracking event not found"));

        TrackingEventResource resource =
                TrackingEventResourceFromEntityAssembler.toResourceFromEntity(event);

        return ResponseEntity.ok(resource);
    }

    // POST /trackingEvents
    @PostMapping
    public ResponseEntity<TrackingEventResource> createTrackingEvent(
            @Valid @RequestBody CreateTrackingEventResource resource) {

        CreateTrackingEventCommand command =
                CreateTrackingEventCommandFromResourceAssembler.toCommandFromResource(resource);

        TrackingEvent event = commandService.handle(command);

        TrackingEventResource eventResource =
                TrackingEventResourceFromEntityAssembler.toResourceFromEntity(event);

        URI location = URI.create("/api/v1/trackingEvents/" + event.getId());

        return ResponseEntity.created(location).body(eventResource);
    }

    // PATCH /trackingEvents/{id}
    @PatchMapping("{trackingEventId}")
    public ResponseEntity<TrackingEventResource> updateTrackingEvent(
            @PathVariable Long trackingEventId,
            @Valid @RequestBody UpdateTrackingEventResource resource) {

        UpdateTrackingEventCommand command =
                UpdateTrackingEventCommandFromResourceAssembler.toCommandFromResource(
                        trackingEventId, resource
                );

        TrackingEvent event;
        try {
            event = commandService.handle(command);
        } catch (TrackingEventNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }

        TrackingEventResource eventResource =
                TrackingEventResourceFromEntityAssembler.toResourceFromEntity(event);

        return ResponseEntity.ok(eventResource);
    }

    // DELETE /trackingEvents/{id}
    @DeleteMapping("{trackingEventId}")
    public ResponseEntity<Map<String, Boolean>> deleteTrackingEvent(
            @PathVariable Long trackingEventId) {

        try {
            commandService.delete(trackingEventId);
        } catch (TrackingEventNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }

        return ResponseEntity.ok(Map.of("success", true));
    }
}
