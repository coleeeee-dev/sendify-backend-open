package com.sendify.platform.auctions.domain.exceptions;

public class AuctionRequestNotFoundException extends RuntimeException {
    public AuctionRequestNotFoundException(Long id) {
        super("Auction request with id %d not found".formatted(id));
    }
}
