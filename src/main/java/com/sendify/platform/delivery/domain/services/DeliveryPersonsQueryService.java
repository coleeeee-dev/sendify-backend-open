package com.sendify.platform.delivery.domain.services;

import com.sendify.platform.delivery.domain.model.aggregates.DeliveryPerson;
import com.sendify.platform.delivery.domain.model.queries.GetAllDeliveryPersonsQuery;
import com.sendify.platform.delivery.domain.model.queries.GetDeliveryPersonByIdQuery;
import com.sendify.platform.delivery.domain.model.queries.GetDeliveryPersonsByCodeQuery;

import java.util.List;
import java.util.Optional;

public interface DeliveryPersonsQueryService {

    Optional<DeliveryPerson> handle(GetDeliveryPersonByIdQuery query);

    List<DeliveryPerson> handle(GetAllDeliveryPersonsQuery query);

    List<DeliveryPerson> handle(GetDeliveryPersonsByCodeQuery query);
}
