package com.sendify.platform.delivery.interfaces.rest;

import com.sendify.platform.delivery.domain.exceptions.DeliveryPersonNotFoundException;
import com.sendify.platform.delivery.domain.model.aggregates.DeliveryPerson;
import com.sendify.platform.delivery.domain.model.commands.CreateDeliveryPersonCommand;
import com.sendify.platform.delivery.domain.model.commands.UpdateDeliveryPersonCommand;
import com.sendify.platform.delivery.domain.model.queries.GetAllDeliveryPersonsQuery;
import com.sendify.platform.delivery.domain.model.queries.GetDeliveryPersonByIdQuery;
import com.sendify.platform.delivery.domain.model.queries.GetDeliveryPersonsByCodeQuery;
import com.sendify.platform.delivery.domain.services.DeliveryPersonsCommandService;
import com.sendify.platform.delivery.domain.services.DeliveryPersonsQueryService;
import com.sendify.platform.delivery.interfaces.rest.resources.CreateDeliveryPersonResource;
import com.sendify.platform.delivery.interfaces.rest.resources.DeliveryPersonResource;
import com.sendify.platform.delivery.interfaces.rest.resources.UpdateDeliveryPersonResource;
import com.sendify.platform.delivery.interfaces.rest.transform.CreateDeliveryPersonCommandFromResourceAssembler;
import com.sendify.platform.delivery.interfaces.rest.transform.DeliveryPersonResourceFromEntityAssembler;
import com.sendify.platform.delivery.interfaces.rest.transform.UpdateDeliveryPersonCommandFromResourceAssembler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/v1/deliveryPersons")
public class DeliveryPersonsController {

    private final DeliveryPersonsCommandService commandService;
    private final DeliveryPersonsQueryService queryService;

    public DeliveryPersonsController(DeliveryPersonsCommandService commandService,
                                     DeliveryPersonsQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    // GET /deliveryPersons?code=XXX
    @GetMapping
    public ResponseEntity<List<DeliveryPersonResource>> getDeliveryPersons(
            @RequestParam(required = false) String code) {

        List<DeliveryPerson> deliveryPersons;

        if (code != null && !code.isBlank()) {
            var query = new GetDeliveryPersonsByCodeQuery(code);
            deliveryPersons = queryService.handle(query);
        } else {
            var query = new GetAllDeliveryPersonsQuery();
            deliveryPersons = queryService.handle(query);
        }

        List<DeliveryPersonResource> resources = deliveryPersons.stream()
                .map(DeliveryPersonResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    // GET /deliveryPersons/{id}
    @GetMapping("{deliveryPersonId}")
    public ResponseEntity<DeliveryPersonResource> getDeliveryPersonById(
            @PathVariable Long deliveryPersonId) {

        var query = new GetDeliveryPersonByIdQuery(deliveryPersonId);

        DeliveryPerson deliveryPerson = queryService.handle(query)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Delivery person not found"));

        DeliveryPersonResource resource =
                DeliveryPersonResourceFromEntityAssembler.toResourceFromEntity(deliveryPerson);

        return ResponseEntity.ok(resource);
    }

    // POST /deliveryPersons
    @PostMapping
    public ResponseEntity<DeliveryPersonResource> createDeliveryPerson(
            @Valid @RequestBody CreateDeliveryPersonResource resource) {

        CreateDeliveryPersonCommand command =
                CreateDeliveryPersonCommandFromResourceAssembler.toCommandFromResource(resource);

        DeliveryPerson deliveryPerson = commandService.handle(command);

        DeliveryPersonResource deliveryPersonResource =
                DeliveryPersonResourceFromEntityAssembler.toResourceFromEntity(deliveryPerson);

        URI location = URI.create("/api/v1/deliveryPersons/" + deliveryPerson.getId());

        return ResponseEntity.created(location).body(deliveryPersonResource);
    }

    // PATCH /deliveryPersons/{id}
    @PatchMapping("{deliveryPersonId}")
    public ResponseEntity<DeliveryPersonResource> updateDeliveryPerson(
            @PathVariable Long deliveryPersonId,
            @Valid @RequestBody UpdateDeliveryPersonResource resource) {

        UpdateDeliveryPersonCommand command =
                UpdateDeliveryPersonCommandFromResourceAssembler.toCommandFromResource(
                        deliveryPersonId, resource
                );

        DeliveryPerson deliveryPerson;
        try {
            deliveryPerson = commandService.handle(command);
        } catch (DeliveryPersonNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }

        DeliveryPersonResource deliveryPersonResource =
                DeliveryPersonResourceFromEntityAssembler.toResourceFromEntity(deliveryPerson);

        return ResponseEntity.ok(deliveryPersonResource);
    }

    // DELETE /deliveryPersons/{id}
    @DeleteMapping("{deliveryPersonId}")
    public ResponseEntity<Map<String, Boolean>> deleteDeliveryPerson(
            @PathVariable Long deliveryPersonId) {

        try {
            commandService.delete(deliveryPersonId);
        } catch (DeliveryPersonNotFoundException ex) {
            throw new ResponseStatusException(NOT_FOUND, ex.getMessage());
        }

        return ResponseEntity.ok(Map.of("success", true));
    }
}
