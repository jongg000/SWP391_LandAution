package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Violation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationRepository extends JpaRepository<Violation, Integer> {
}
