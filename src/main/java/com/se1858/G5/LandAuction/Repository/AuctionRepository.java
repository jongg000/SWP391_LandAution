package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {

    List<Auction> findTop4ByOrderByStartTimeDesc();
    List<Auction> findAllByEndTimeBefore(LocalDateTime endTime);
}
