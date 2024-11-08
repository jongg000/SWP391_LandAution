package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {

    List<Auction> findTop4ByOrderByStartTimeDesc();
    List<Auction> findAllByEndTimeBefore(LocalDateTime endTime);
    List<Auction> findAllByOrderByStartTimeDesc();
    long countByStatus(Status status);
    Auction findAuctionByLand(Land land);

}
