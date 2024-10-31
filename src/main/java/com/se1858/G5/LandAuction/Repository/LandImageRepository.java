package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.LandImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LandImageRepository extends JpaRepository<LandImage, Integer> {
    List<LandImage> findByLand_LandId(int landId);
}
