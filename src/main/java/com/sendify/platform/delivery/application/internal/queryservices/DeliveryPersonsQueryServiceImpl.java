package com.sendify.platform.delivery.application.internal.queryservices;

import com.sendify.platform.delivery.domain.model.aggregates.DeliveryPerson;
import com.sendify.platform.delivery.domain.model.queries.GetAllDeliveryPersonsQuery;
import com.sendify.platform.delivery.domain.model.queries.GetDeliveryPersonByIdQuery;
import com.sendify.platform.delivery.domain.model.queries.GetDeliveryPersonsByCodeQuery;
import com.sendify.platform.delivery.domain.services.DeliveryPersonsQueryService;
import com.sendify.platform.delivery.infrastructure.persistence.jpa.repositories.DeliveryPersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeliveryPersonsQueryServiceImpl implements DeliveryPersonsQueryService {

    private final DeliveryPersonRepository deliveryPersonRepository;

    public DeliveryPersonsQueryServiceImpl(DeliveryPersonRepository deliveryPersonRepository) {
        this.deliveryPersonRepository = deliveryPersonRepository;
    }

    @Override
    public Optional<DeliveryPerson> handle(GetDeliveryPersonByIdQuery query) {
        return deliveryPersonRepository.findById(query.deliveryPersonId());
    }

    @Override
    public List<DeliveryPerson> handle(GetAllDeliveryPersonsQuery query) {
        return deliveryPersonRepository.findAll();
    }

    @Override
    public List<DeliveryPerson> handle(GetDeliveryPersonsByCodeQuery query) {
        if (query.code() == null || query.code().isBlank()) {
            return deliveryPersonRepository.findAll();
        }
        String code = query.code().trim().toUpperCase();
        return deliveryPersonRepository.findAllByCodeValue(code);
    }
}
