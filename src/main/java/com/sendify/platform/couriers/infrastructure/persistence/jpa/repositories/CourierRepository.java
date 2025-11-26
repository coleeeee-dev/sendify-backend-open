package com.sendify.platform.couriers.infrastructure.persistence.jpa.repositories;

import com.sendify.platform.couriers.domain.model.aggregates.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
}
