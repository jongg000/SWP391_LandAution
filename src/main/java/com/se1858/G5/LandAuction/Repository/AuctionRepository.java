package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    Auction findByAuctionId(int auctionId);
    List<Auction> findTop4ByOrderByStartTimeDesc();
    List<Auction> findAllByEndTimeBefore(LocalDateTime endTime);
    List<Auction> findAllByOrderByStartTimeDesc();
    long countByStatus(Status status);
    List<Auction> findAuctionByLand(Land land);
    List<Auction> findTop6ByOrderByAuctionIdDesc();
    @Query("SELECT a FROM Auction a "
            + "JOIN a.land l "
            + "JOIN AssetRegistration ar ON ar.land = l "
            + "WHERE a.status.statusID != 4 AND a.status.statusID != 9 "
            + "ORDER BY ar.approvalDate DESC")
    List<Auction> findAllActiveAuctionsWithApprovalDate();
}
