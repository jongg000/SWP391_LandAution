package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.DTO.Request.AuctionRequest;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private LandRepository landRepository;

    public List<AuctionRequest> getAllAuctions() {
        return auctionRepository.findAll().
                stream().map(this::convertToDTO).
                collect(Collectors.toList());
    }

    public AuctionRequest findAuctionById(int auctionId) {
        return auctionRepository.findById(auctionId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public void deleteAuction(int auctionId) {
        auctionRepository.deleteById(auctionId);
    }

    public AuctionRequest update(AuctionRequest auctionRequest) {
        Auction auction = convertToEntity(auctionRequest);
        Auction auctionUpdate = auctionRepository.save(auction);
        return convertToDTO(auctionUpdate);
    }

    private AuctionRequest convertToDTO(Auction auction) {
        return AuctionRequest.builder().auctionId(auction.getAuctionId()).
                landId(auction.getLand().getLandId()).
                status(auction.getStatus()).
                startTime(auction.getStartTime()).
                endTime(auction.getEndTime()).
                highestBid(auction.getHighestBid()).
                build();
    }

    private Auction convertToEntity(AuctionRequest auctionRequest) {
        Land land = landRepository.findById(auctionRequest.getLandId()).orElse(null);
        return Auction.builder()
                .auctionId(auctionRequest.getAuctionId())
                .land(land)
                .status(auctionRequest.getStatus())
                .startTime(auctionRequest.getStartTime())
                .endTime(auctionRequest.getEndTime())
                .highestBid(auctionRequest.getHighestBid())
                .build();
    }

}
