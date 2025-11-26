package com.sendify.platform.shipments.domain.services;

import com.sendify.platform.shipments.domain.model.aggregates.Shipment;
import com.sendify.platform.shipments.domain.model.queries.GetShipmentByIdQuery;
import com.sendify.platform.shipments.domain.model.queries.GetShipmentsQuery;

import java.util.List;
import java.util.Optional;

public interface ShipmentsQueryService {

    Optional<Shipment> handle(GetShipmentByIdQuery query);

    List<Shipment> handle(GetShipmentsQuery query);
}
