package com.sendify.platform.delivery.infrastructure.persistence.jpa.repositories;

import com.sendify.platform.delivery.domain.model.aggregates.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {

    Optional<DeliveryPerson> findByCodeValue(String code);

    boolean existsByCodeValue(String code);

    List<DeliveryPerson> findAllByCodeValue(String code);
}
