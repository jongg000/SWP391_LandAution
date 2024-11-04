package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.AuctionDto;
import com.se1858.G5.LandAuction.Entity.Auction;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionService {
    public AuctionDto findAuctionById(int auctionId);
    public List<AuctionDto> getAllAuctions();
    public List<AuctionDto> getTop4NewestAuctions();
    public void deleteAuction(int auctionId);
    public AuctionDto update(AuctionDto auctionDto);
    public boolean checkExistUserInAuction(int userId, int auctionId);
    public long getTotalAuctions();
    public AuctionDto convertToDTO(Auction auction);
    public void scheduledUpdateHighestBid();
    public List<Auction> findAllAuctionEnd();
}
