package com.sendify.platform.auctions.infrastructure.persistence.jpa.repositories;

import com.sendify.platform.auctions.domain.model.aggregates.AuctionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRequestRepository extends JpaRepository<AuctionRequest, Long> {
}
