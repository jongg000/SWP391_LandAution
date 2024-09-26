package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
}
