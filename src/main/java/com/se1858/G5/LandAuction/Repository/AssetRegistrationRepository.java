package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.Status;
import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRegistrationRepository extends JpaRepository<AssetRegistration,Integer> {
    List<AssetRegistration> findByStatus(Status status);
    AssetRegistration findAssetRegistrationByLand_LandId(int landId);
    List<AssetRegistration> findByUser(User user);
    Land findByLand(Land land);
}
