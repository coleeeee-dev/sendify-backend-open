package com.sendify.platform.delivery.domain.services;

import com.sendify.platform.delivery.domain.model.aggregates.DeliveryPerson;
import com.sendify.platform.delivery.domain.model.commands.CreateDeliveryPersonCommand;
import com.sendify.platform.delivery.domain.model.commands.UpdateDeliveryPersonCommand;

public interface DeliveryPersonsCommandService {

    DeliveryPerson handle(CreateDeliveryPersonCommand command);

    DeliveryPerson handle(UpdateDeliveryPersonCommand command);

    void delete(Long deliveryPersonId);
}
