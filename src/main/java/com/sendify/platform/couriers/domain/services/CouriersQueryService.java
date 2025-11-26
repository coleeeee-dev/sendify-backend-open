package com.sendify.platform.couriers.domain.services;

import com.sendify.platform.couriers.domain.model.aggregates.Courier;
import com.sendify.platform.couriers.domain.model.queries.GetAllCouriersQuery;
import com.sendify.platform.couriers.domain.model.queries.GetCourierByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CouriersQueryService {

    Optional<Courier> handle(GetCourierByIdQuery query);

    List<Courier> handle(GetAllCouriersQuery query);
}
