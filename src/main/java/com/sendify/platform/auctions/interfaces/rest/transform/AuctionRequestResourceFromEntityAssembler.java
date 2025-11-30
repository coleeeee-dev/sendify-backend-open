package com.sendify.platform.auctions.interfaces.rest.transform;

import com.sendify.platform.auctions.domain.model.aggregates.AuctionRequest;
import com.sendify.platform.auctions.interfaces.rest.resources.AuctionRequestResource;

public class AuctionRequestResourceFromEntityAssembler {

    public static AuctionRequestResource toResourceFromEntity(AuctionRequest entity) {
        return new AuctionRequestResource(
                entity.getId(),
                entity.getOrigin(),
                entity.getDestination(),
                entity.getWeightKg(),
                entity.getLengthCm(),
                entity.getWidthCm(),
                entity.getHeightCm(),
                entity.getDeclaredValue(),
                entity.getDescription(),
                entity.getStatus().name(),
                entity.getCreatedAt()
        );
    }
}
