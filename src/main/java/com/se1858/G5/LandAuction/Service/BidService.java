package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.AuctionDto;
import com.se1858.G5.LandAuction.DTO.AuctionRegistrationDTO;
import com.se1858.G5.LandAuction.DTO.BidDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidService {
    @Autowired
    private BidsRepository bidRepository;



    @Autowired
    private AuctionRegistrationRepository auctionRegistrationRepository;

    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private AuctionService auctionService;

    private BidDTO convertToDTO(Bids bid) {
        return BidDTO.builder()
                .bidAmount(bid.getBidAmount())
                .registrationId(bid.getAuctionRegistration().getRegistrationID())
                .bidTime(bid.getBidTime())
                .build();
    }

    private Bids convertToEntity(BidDTO bid) {
        return Bids.builder()
                .bidAmount(bid.getBidAmount())
                .auctionRegistration(auctionRegistrationRepository.findById(bid.getRegistrationId()).orElse(null))
                .bidTime(bid.getBidTime())
                .build();
    }

    public Bids saveBid(BidDTO bid) {
        return bidRepository.save(convertToEntity(bid));
    }

    public List<BidDTO> getAllBids(int auctionRegistrationId) {
        return bidRepository.findAllByAuctionRegistration_Auction_AuctionIdOrderByBidTimeDesc(auctionRegistrationId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }




    public boolean checkWinner(int auctionRegistrationId) {
        AuctionRegistration ar = auctionRegistrationRepository.findById(auctionRegistrationId).orElse(null);
        int auctionId = ar.getAuction().getAuctionId();
        int userId = ar.getUser().getUserId();
        BidDTO bid = convertToDTO(bidRepository.findTop1ByAuctionRegistration_User_UserIdAndAuctionRegistration_Auction_AuctionIdOrderByBidAmountDesc(userId,auctionId));
        AuctionDto auctionDto = auctionService.convertToDTO(auctionRepository.findById(auctionId).orElse(null));
        return bid.getBidAmount() == auctionDto.getHighestBid();
    }

    public Bids findBidByAuctionAndBidAmount(Auction auction, long bidAmount) {
        return bidRepository.findByAuctionRegistration_AuctionAndBidAmount(auction, bidAmount);
    }

}

