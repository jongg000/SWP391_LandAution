package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.AuctionDto;
import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionService {
    public AuctionDto findAuctionById(int auctionId);
    public List<AuctionDto> getAllAuctions();
    public List<AuctionDto> getTop4NewestAuctions();
    public void deleteAuction(int auctionId);
    public void update(Auction auction);
    public boolean checkExistUserInAuction(int userId, int auctionId);
    public long getTotalAuctions();
    public AuctionDto convertToDTO(Auction auction);
    public void scheduledUpdateHighestBid();
    public List<Auction> findAllAuctionEnd();
    public void saveAuction(Auction auction);
    public List<Auction> getAllAuctionByStartTime();
    public long countByStatus(Status status);
    List<Auction> findTop5AuctionsByAuctionIdDesc();
    public boolean checkWinner(int auctionId, int userId);
    public Auction findAuctionByLand(Land land);
}
