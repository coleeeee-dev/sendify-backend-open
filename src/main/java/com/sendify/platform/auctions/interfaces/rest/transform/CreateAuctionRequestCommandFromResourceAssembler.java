package com.sendify.platform.auctions.interfaces.rest.transform;

import com.sendify.platform.auctions.domain.model.commands.CreateAuctionRequestCommand;
import com.sendify.platform.auctions.interfaces.rest.resources.CreateAuctionRequestResource;

public class CreateAuctionRequestCommandFromResourceAssembler {

    public static CreateAuctionRequestCommand toCommandFromResource(
            CreateAuctionRequestResource resource) {

        return new CreateAuctionRequestCommand(
                resource.origin(),
                resource.destination(),
                resource.weightKg(),
                resource.lengthCm(),
                resource.widthCm(),
                resource.heightCm(),
                resource.declaredValue(),
                resource.description()
        );
    }
}
