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


public interface BidService {

     BidDTO convertToDTO(Bids bid);

     Bids convertToEntity(BidDTO bid);

    public Bids saveBid(BidDTO bid);
    public List<BidDTO> getAllBids(int auctionRegistrationId);




    public boolean checkWinner(int auctionRegistrationId);

    public Bids findBidByAuctionAndBidAmount(Auction auction, long bidAmount);

}

