package com.sendify.platform.auctions.interfaces.rest;

import com.sendify.platform.auctions.domain.exceptions.AuctionRequestNotFoundException;
import com.sendify.platform.auctions.domain.model.aggregates.AuctionRequest;
import com.sendify.platform.auctions.domain.model.commands.CreateAuctionRequestCommand;
import com.sendify.platform.auctions.domain.model.queries.GetAuctionRequestByIdQuery;
import com.sendify.platform.auctions.domain.model.queries.GetAuctionRequestsQuery;
import com.sendify.platform.auctions.domain.services.AuctionRequestsCommandService;
import com.sendify.platform.auctions.domain.services.AuctionRequestsQueryService;
import com.sendify.platform.auctions.interfaces.rest.resources.AuctionRequestResource;
import com.sendify.platform.auctions.interfaces.rest.resources.CreateAuctionRequestResource;
import com.sendify.platform.auctions.interfaces.rest.transform.AuctionRequestResourceFromEntityAssembler;
import com.sendify.platform.auctions.interfaces.rest.transform.CreateAuctionRequestCommandFromResourceAssembler;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auction-requests")
public class AuctionRequestsController {

    private final AuctionRequestsCommandService commandService;
    private final AuctionRequestsQueryService queryService;

    public AuctionRequestsController(AuctionRequestsCommandService commandService,
                                     AuctionRequestsQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    public ResponseEntity<AuctionRequestResource> createAuctionRequest(
            @Valid @RequestBody CreateAuctionRequestResource resource) {

        CreateAuctionRequestCommand command =
                CreateAuctionRequestCommandFromResourceAssembler.toCommandFromResource(resource);

        AuctionRequest auctionRequest = commandService.handle(command);

        AuctionRequestResource responseResource =
                AuctionRequestResourceFromEntityAssembler.toResourceFromEntity(auctionRequest);

        URI location = URI.create("/api/v1/auction-requests/" + auctionRequest.getId());

        return ResponseEntity.created(location).body(responseResource);
    }

    @GetMapping
    public ResponseEntity<List<AuctionRequestResource>> getAllAuctionRequests() {
        var query = new GetAuctionRequestsQuery();
        List<AuctionRequest> auctionRequests = queryService.handle(query);

        List<AuctionRequestResource> resources = auctionRequests.stream()
                .map(AuctionRequestResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("{auctionRequestId}")
    public ResponseEntity<AuctionRequestResource> getAuctionRequestById(
            @PathVariable Long auctionRequestId) {

        var query = new GetAuctionRequestByIdQuery(auctionRequestId);

        AuctionRequest auctionRequest = queryService.handle(query)
                .orElseThrow(() -> new AuctionRequestNotFoundException(auctionRequestId));

        AuctionRequestResource resource =
                AuctionRequestResourceFromEntityAssembler.toResourceFromEntity(auctionRequest);

        return ResponseEntity.ok(resource);
    }
}
