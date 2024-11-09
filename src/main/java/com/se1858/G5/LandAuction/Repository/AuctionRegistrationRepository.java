package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Entity.AuctionRegistration;
import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRegistrationRepository extends JpaRepository<AuctionRegistration, Integer> {
    boolean existsByUser_UserIdAndAuction_AuctionId(int userId, int auctionId);
    List<AuctionRegistration> findAllByAuction_AuctionId(int auctionId);
    AuctionRegistration findByUser_UserIdAndAuction_AuctionId(int userId,int auctionId);
    List<AuctionRegistration> findAllByUser_UserId(int userId);
    List<User> getUsersByAuction_AuctionId(int auctionId);
    AuctionRegistration findByAuctionAndBids_BidAmount(Auction auction, long bidAmount);
}
