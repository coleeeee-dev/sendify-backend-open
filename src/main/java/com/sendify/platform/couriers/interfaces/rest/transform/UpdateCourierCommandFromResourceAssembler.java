package com.sendify.platform.couriers.interfaces.rest.transform;

import com.sendify.platform.couriers.domain.model.commands.UpdateCourierCommand;
import com.sendify.platform.couriers.interfaces.rest.resources.UpdateCourierResource;

public class UpdateCourierCommandFromResourceAssembler {

    public static UpdateCourierCommand toCommandFromResource(
            Long courierId,
            UpdateCourierResource resource) {

        return new UpdateCourierCommand(
                courierId,
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
