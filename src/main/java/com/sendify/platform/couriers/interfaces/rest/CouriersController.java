package com.sendify.platform.couriers.interfaces.rest;

import com.sendify.platform.couriers.domain.exceptions.CourierNotFoundException;
import com.sendify.platform.couriers.domain.model.aggregates.Courier;
import com.sendify.platform.couriers.domain.model.commands.CreateCourierCommand;
import com.sendify.platform.couriers.domain.model.commands.UpdateCourierCommand;
import com.sendify.platform.couriers.domain.model.queries.GetAllCouriersQuery;
import com.sendify.platform.couriers.domain.model.queries.GetCourierByIdQuery;
import com.sendify.platform.couriers.domain.services.CouriersCommandService;
import com.sendify.platform.couriers.domain.services.CouriersQueryService;
import com.sendify.platform.couriers.interfaces.rest.resources.CourierResource;
import com.sendify.platform.couriers.interfaces.rest.resources.CreateCourierResource;
import com.sendify.platform.couriers.interfaces.rest.resources.UpdateCourierResource;
import com.sendify.platform.couriers.interfaces.rest.transform.CourierResourceFromEntityAssembler;
import com.sendify.platform.couriers.interfaces.rest.transform.CreateCourierCommandFromResourceAssembler;
import com.sendify.platform.couriers.interfaces.rest.transform.UpdateCourierCommandFromResourceAssembler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/couriers")
public class CouriersController {

    private final CouriersCommandService commandService;
    private final CouriersQueryService queryService;

    public CouriersController(CouriersCommandService commandService,
                              CouriersQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<CourierResource> createCourier(
            @Valid @RequestBody CreateCourierResource resource) {

        CreateCourierCommand command =
                CreateCourierCommandFromResourceAssembler.toCommandFromResource(resource);

        Courier courier = commandService.handle(command);

        CourierResource courierResource =
                CourierResourceFromEntityAssembler.toResourceFromEntity(courier);

        URI location = URI.create("/api/v1/couriers/" + courier.getId());

        return ResponseEntity.created(location).body(courierResource);
    }

    @GetMapping
    public ResponseEntity<List<CourierResource>> getAllCouriers() {
        var query = new GetAllCouriersQuery();
        List<Courier> couriers = queryService.handle(query);

        List<CourierResource> resources = couriers.stream()
                .map(CourierResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("{courierId}")
    public ResponseEntity<CourierResource> getCourierById(@PathVariable Long courierId) {
        var query = new GetCourierByIdQuery(courierId);

        Courier courier = queryService.handle(query)
                .orElseThrow(() -> new CourierNotFoundException(courierId));

        CourierResource resource =
                CourierResourceFromEntityAssembler.toResourceFromEntity(courier);

        return ResponseEntity.ok(resource);
    }

    @PutMapping("{courierId}")
    public ResponseEntity<CourierResource> updateCourier(
            @PathVariable Long courierId,
            @Valid @RequestBody UpdateCourierResource resource) {

        UpdateCourierCommand command =
                UpdateCourierCommandFromResourceAssembler.toCommandFromResource(courierId, resource);

        Courier courier = commandService.handle(command);

        CourierResource courierResource =
                CourierResourceFromEntityAssembler.toResourceFromEntity(courier);

        return ResponseEntity.ok(courierResource);
    }
}
