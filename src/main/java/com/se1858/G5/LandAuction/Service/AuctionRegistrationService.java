package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.AuctionRegistrationDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public interface AuctionRegistrationService {

     AuctionRegistrationDTO convertToDTO(AuctionRegistration auction);

     AuctionRegistration convertToEntity(AuctionRegistrationDTO land);
    public AuctionRegistrationDTO getById(int id);

    public AuctionRegistration save(AuctionRegistrationDTO auctionDto);

    public List<AuctionRegistrationDTO> getAllAuctionRegistrationsByAuctionId(int auctionId);

    public AuctionRegistrationDTO getByUser_UserIdAndAuction_AuctionId(int userId, int auctionId);
    public List<AuctionRegistrationDTO> getAllAuctionRegistrationsByUserId(int userId);
    public boolean checkAvailableAttend(int userId, int landId);
    public AuctionRegistration getByAuctionAndBidAmount( Auction auction, long bidAmount);

    public boolean checkAvailableAttend(int userId);
}


