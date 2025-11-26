package com.sendify.platform.couriers.application.internal.queryservices;

import com.sendify.platform.couriers.domain.model.aggregates.Courier;
import com.sendify.platform.couriers.domain.model.queries.GetAllCouriersQuery;
import com.sendify.platform.couriers.domain.model.queries.GetCourierByIdQuery;
import com.sendify.platform.couriers.domain.services.CouriersQueryService;
import com.sendify.platform.couriers.infrastructure.persistence.jpa.repositories.CourierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CouriersQueryServiceImpl implements CouriersQueryService {

    private final CourierRepository courierRepository;

    public CouriersQueryServiceImpl(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    public Optional<Courier> handle(GetCourierByIdQuery query) {
        return courierRepository.findById(query.courierId());
    }

    @Override
    public List<Courier> handle(GetAllCouriersQuery query) {
        return courierRepository.findAll();
    }
}
