package com.sendify.platform.couriers.interfaces.rest.transform;

import com.sendify.platform.couriers.domain.model.aggregates.Courier;
import com.sendify.platform.couriers.interfaces.rest.resources.CourierResource;

public class CourierResourceFromEntityAssembler {

    public static CourierResource toResourceFromEntity(Courier courier) {
        return new CourierResource(
                courier.getId(),
                courier.getName(),
                courier.getContactEmail().getValue(),
                courier.getContactPhone().getValue(),
                courier.getWebsiteUrl() != null ? courier.getWebsiteUrl().getValue() : null,
                courier.getCostPerKg().getAmount(),
                courier.getCostPerKg().getCurrency(),
                courier.getEstimatedDeliveryDays()
        );
    }
}
