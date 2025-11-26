package com.sendify.platform.shipments.domain.services;

import com.sendify.platform.shipments.domain.model.aggregates.Shipment;
import com.sendify.platform.shipments.domain.model.commands.CreateShipmentCommand;
import com.sendify.platform.shipments.domain.model.commands.DeleteShipmentCommand;
import com.sendify.platform.shipments.domain.model.commands.UpdateShipmentStatusCommand;

public interface ShipmentsCommandService {

    Shipment handle(CreateShipmentCommand command);

    Shipment handle(UpdateShipmentStatusCommand command);

    void handle(DeleteShipmentCommand command);
}
