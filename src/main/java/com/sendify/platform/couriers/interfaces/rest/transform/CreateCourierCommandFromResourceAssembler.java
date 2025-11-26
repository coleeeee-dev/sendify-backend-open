package com.sendify.platform.couriers.interfaces.rest.transform;

import com.sendify.platform.couriers.domain.model.commands.CreateCourierCommand;
import com.sendify.platform.couriers.interfaces.rest.resources.CreateCourierResource;

public class CreateCourierCommandFromResourceAssembler {

    public static CreateCourierCommand toCommandFromResource(CreateCourierResource resource) {
        return new CreateCourierCommand(
                resource.name(),
                resource.contactEmail(),
                resource.contactPhone(),
                resource.websiteUrl(),
                resource.costPerKg(),
                resource.currency(),
                resource.estimatedDeliveryDays()
        );
    }
}
