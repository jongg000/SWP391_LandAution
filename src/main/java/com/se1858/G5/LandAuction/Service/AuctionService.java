package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.DTO.AuctionDto;
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

    public List<AuctionDto> getAllAuctions() {
        return auctionRepository.findAll().
                stream().map(this::convertToDTO).
                collect(Collectors.toList());
    }

    public AuctionDto findAuctionById(int auctionId) {
        return auctionRepository.findById(auctionId)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public void deleteAuction(int auctionId) {
        auctionRepository.deleteById(auctionId);
    }

    public AuctionDto update(AuctionDto auctionDto) {
        Auction auction = convertToEntity(auctionDto);
        Auction auctionUpdate = auctionRepository.save(auction);
        return convertToDTO(auctionUpdate);
    }

    private AuctionDto convertToDTO(Auction auction) {
        return AuctionDto.builder().auctionId(auction.getAuctionId()).
                landId(auction.getLand().getLandId()).
                startTime(auction.getStartTime()).
                endTime(auction.getEndTime()).
                highestBid(auction.getHighestBid()).
                build();
    }

    private Auction convertToEntity(AuctionDto auctionDto) {
        Land land = landRepository.findById(auctionDto.getLandId()).orElse(null);
        return Auction.builder()
                .auctionId(auctionDto.getAuctionId())
                .land(land)
                .startTime(auctionDto.getStartTime())
                .endTime(auctionDto.getEndTime())
                .highestBid(auctionDto.getHighestBid())
                .build();
    }

}
