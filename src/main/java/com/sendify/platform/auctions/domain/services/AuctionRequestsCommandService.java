package com.sendify.platform.auctions.domain.services;

import com.sendify.platform.auctions.domain.model.aggregates.AuctionRequest;
import com.sendify.platform.auctions.domain.model.commands.CreateAuctionRequestCommand;

public interface AuctionRequestsCommandService {

    AuctionRequest handle(CreateAuctionRequestCommand command);
}
