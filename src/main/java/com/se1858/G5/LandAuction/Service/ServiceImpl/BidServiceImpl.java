package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.DTO.AuctionDto;
import com.se1858.G5.LandAuction.DTO.BidDTO;
import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Entity.AuctionRegistration;
import com.se1858.G5.LandAuction.Entity.Bids;
import com.se1858.G5.LandAuction.Repository.AuctionRegistrationRepository;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import com.se1858.G5.LandAuction.Repository.BidsRepository;
import com.se1858.G5.LandAuction.Service.AuctionService;
import com.se1858.G5.LandAuction.Service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private BidsRepository bidRepository;



    @Autowired
    private AuctionRegistrationRepository auctionRegistrationRepository;

    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private AuctionService auctionService;


    @Override
    public BidDTO convertToDTO(Bids bid) {
        return BidDTO.builder()
                .bidAmount(bid.getBidAmount())
                .registrationId(bid.getAuctionRegistration().getRegistrationID())
                .bidTime(bid.getBidTime())
                .build();
    }

    @Override
    public Bids convertToEntity(BidDTO bid) {
        return Bids.builder()
                .bidAmount(bid.getBidAmount())
                .auctionRegistration(auctionRegistrationRepository.findById(bid.getRegistrationId()).orElse(null))
                .bidTime(bid.getBidTime())
                .build();
    }
    @Override
    public Bids saveBid(BidDTO bid) {
        return bidRepository.save(convertToEntity(bid));
    }
    @Override
    public List<BidDTO> getAllBids(int auctionRegistrationId) {
        return bidRepository.findAllByAuctionRegistration_Auction_AuctionIdOrderByBidTimeDesc(auctionRegistrationId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }



    @Override
    public boolean checkWinner(int auctionRegistrationId) {
        AuctionRegistration ar = auctionRegistrationRepository.findById(auctionRegistrationId).orElse(null);
        int auctionId = ar.getAuction().getAuctionId();
        int userId = ar.getUser().getUserId();
        BidDTO bid = convertToDTO(bidRepository.findTop1ByAuctionRegistration_User_UserIdAndAuctionRegistration_Auction_AuctionIdOrderByBidAmountDesc(userId,auctionId));
        AuctionDto auctionDto = auctionService.convertToDTO(auctionRepository.findById(auctionId).orElse(null));
        return bid.getBidAmount() == auctionDto.getHighestBid();
    }
    @Override
    public Bids findBidByAuctionAndBidAmount(Auction auction, long bidAmount) {
        return bidRepository.findByAuctionRegistration_AuctionAndBidAmount(auction, bidAmount);
    }
}
