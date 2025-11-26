package com.sendify.platform.delivery.interfaces.rest.transform;

import com.sendify.platform.delivery.domain.model.aggregates.DeliveryPerson;
import com.sendify.platform.delivery.interfaces.rest.resources.DeliveryPersonResource;

public class DeliveryPersonResourceFromEntityAssembler {

    public static DeliveryPersonResource toResourceFromEntity(DeliveryPerson deliveryPerson) {
        return new DeliveryPersonResource(
                deliveryPerson.getId(),
                deliveryPerson.getName(),
                deliveryPerson.getCode().getValue(),
                deliveryPerson.getPhone().getValue(),
                deliveryPerson.getAssignedShipments(),
                deliveryPerson.isActive()
        );
    }
}
