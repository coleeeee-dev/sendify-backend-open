package com.sendify.platform.auctions.application.internal.queryservices;

import com.sendify.platform.auctions.domain.model.aggregates.AuctionRequest;
import com.sendify.platform.auctions.domain.model.queries.GetAuctionRequestByIdQuery;
import com.sendify.platform.auctions.domain.model.queries.GetAuctionRequestsQuery;
import com.sendify.platform.auctions.domain.services.AuctionRequestsQueryService;
import com.sendify.platform.auctions.infrastructure.persistence.jpa.repositories.AuctionRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuctionRequestsQueryServiceImpl implements AuctionRequestsQueryService {

    private final AuctionRequestRepository auctionRequestRepository;

    public AuctionRequestsQueryServiceImpl(AuctionRequestRepository auctionRequestRepository) {
        this.auctionRequestRepository = auctionRequestRepository;
    }

    @Override
    public Optional<AuctionRequest> handle(GetAuctionRequestByIdQuery query) {
        return auctionRequestRepository.findById(query.auctionRequestId());
    }

    @Override
    public List<AuctionRequest> handle(GetAuctionRequestsQuery query) {
        // Por ahora sin filtros, retornamos todo.
        return auctionRequestRepository.findAll();
    }
}
