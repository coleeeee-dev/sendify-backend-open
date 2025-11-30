package com.sendify.platform.auctions.domain.services;

import com.sendify.platform.auctions.domain.model.aggregates.AuctionRequest;
import com.sendify.platform.auctions.domain.model.queries.GetAuctionRequestByIdQuery;
import com.sendify.platform.auctions.domain.model.queries.GetAuctionRequestsQuery;

import java.util.List;
import java.util.Optional;

public interface AuctionRequestsQueryService {

    Optional<AuctionRequest> handle(GetAuctionRequestByIdQuery query);

    List<AuctionRequest> handle(GetAuctionRequestsQuery query);
}
