package com.sendify.platform.auctions.application.internal.commandservices;

import com.sendify.platform.auctions.domain.model.aggregates.AuctionRequest;
import com.sendify.platform.auctions.domain.model.commands.CreateAuctionRequestCommand;
import com.sendify.platform.auctions.domain.services.AuctionRequestsCommandService;
import com.sendify.platform.auctions.infrastructure.persistence.jpa.repositories.AuctionRequestRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuctionRequestsCommandServiceImpl implements AuctionRequestsCommandService {

    private final AuctionRequestRepository auctionRequestRepository;

    public AuctionRequestsCommandServiceImpl(AuctionRequestRepository auctionRequestRepository) {
        this.auctionRequestRepository = auctionRequestRepository;
    }

    @Override
    public AuctionRequest handle(CreateAuctionRequestCommand command) {
        AuctionRequest auctionRequest = AuctionRequest.create(
                command.origin(),
                command.destination(),
                command.weightKg(),
                command.lengthCm(),
                command.widthCm(),
                command.heightCm(),
                command.declaredValue(),
                command.description()
        );

        return auctionRequestRepository.save(auctionRequest);
    }
}
