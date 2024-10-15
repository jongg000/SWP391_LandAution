package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.AuctionRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionRegistrationRepository extends JpaRepository<AuctionRegistration, Integer> {
}
