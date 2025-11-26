package com.sendify.platform.shipments.interfaces.rest;

import com.sendify.platform.shipments.domain.exceptions.ShipmentNotFoundException;
import com.sendify.platform.shipments.domain.model.aggregates.Shipment;
import com.sendify.platform.shipments.domain.model.commands.CreateShipmentCommand;
import com.sendify.platform.shipments.domain.model.commands.DeleteShipmentCommand;
import com.sendify.platform.shipments.domain.model.commands.UpdateShipmentStatusCommand;
import com.sendify.platform.shipments.domain.model.queries.GetShipmentByIdQuery;
import com.sendify.platform.shipments.domain.model.queries.GetShipmentsQuery;
import com.sendify.platform.shipments.domain.services.ShipmentsCommandService;
import com.sendify.platform.shipments.domain.services.ShipmentsQueryService;
import com.sendify.platform.shipments.interfaces.rest.resources.CreateShipmentResource;
import com.sendify.platform.shipments.interfaces.rest.resources.ShipmentResource;
import com.sendify.platform.shipments.interfaces.rest.resources.UpdateShipmentResource;
import com.sendify.platform.shipments.interfaces.rest.transform.CreateShipmentCommandFromResourceAssembler;
import com.sendify.platform.shipments.interfaces.rest.transform.ShipmentResourceFromEntityAssembler;
import com.sendify.platform.shipments.interfaces.rest.transform.UpdateShipmentStatusCommandFromResourceAssembler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/shipments")
public class ShipmentsController {

    private final ShipmentsCommandService commandService;
    private final ShipmentsQueryService queryService;

    public ShipmentsController(ShipmentsCommandService commandService,
                               ShipmentsQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<ShipmentResource>> getShipments(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String trackingCode,
            @RequestParam(required = false) String deliveryPersonId) {

        var query = new GetShipmentsQuery(status, trackingCode, deliveryPersonId);
        List<Shipment> shipments = queryService.handle(query);

        List<ShipmentResource> resources = shipments.stream()
                .map(ShipmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("{shipmentId}")
    public ResponseEntity<ShipmentResource> getShipmentById(@PathVariable Long shipmentId) {
        var query = new GetShipmentByIdQuery(shipmentId);

        Shipment shipment = queryService.handle(query)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Shipment not found"));

        ShipmentResource resource = ShipmentResourceFromEntityAssembler.toResourceFromEntity(shipment);

        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<ShipmentResource> createShipment(
            @Valid @RequestBody CreateShipmentResource resource) {

        CreateShipmentCommand command =
                CreateShipmentCommandFromResourceAssembler.toCommandFromResource(resource);

        Shipment shipment = commandService.handle(command);

        ShipmentResource shipmentResource =
                ShipmentResourceFromEntityAssembler.toResourceFromEntity(shipment);

        URI location = URI.create("/api/v1/shipments/" + shipment.getId());

        return ResponseEntity.created(location).body(shipmentResource);
    }

    @PatchMapping("{shipmentId}")
    public ResponseEntity<ShipmentResource> updateShipmentStatus(
            @PathVariable Long shipmentId,
            @Valid @RequestBody UpdateShipmentResource resource) {

        UpdateShipmentStatusCommand command =
                UpdateShipmentStatusCommandFromResourceAssembler.toCommandFromResource(shipmentId, resource);

        Shipment shipment = commandService.handle(command);

        ShipmentResource shipmentResource =
                ShipmentResourceFromEntityAssembler.toResourceFromEntity(shipment);

        return ResponseEntity.ok(shipmentResource);
    }

    @DeleteMapping("{shipmentId}")
    public ResponseEntity<Void> deleteShipment(@PathVariable Long shipmentId) {
        try {
            commandService.handle(new DeleteShipmentCommand(shipmentId));
        } catch (ShipmentNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
