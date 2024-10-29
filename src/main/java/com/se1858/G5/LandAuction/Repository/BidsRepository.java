package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Bids;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidsRepository extends JpaRepository<Bids, Integer> {
  List<Bids> findAllByAuctionRegistration_Auction_AuctionIdOrderByBidTimeDesc(int auctionRegistrationId);

    Bids findTop1ByAuctionRegistration_User_UserIdAndAuctionRegistration_Auction_AuctionIdOrderByBidAmountDesc(int userId, int auctionId);
}