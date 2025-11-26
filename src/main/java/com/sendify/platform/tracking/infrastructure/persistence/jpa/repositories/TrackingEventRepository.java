package com.sendify.platform.tracking.infrastructure.persistence.jpa.repositories;

import com.sendify.platform.tracking.domain.model.aggregates.TrackingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {

    List<TrackingEvent> findAllByShipmentId(Long shipmentId);
}
