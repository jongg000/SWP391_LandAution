package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.PersistentLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersistentLoginRepository extends JpaRepository<PersistentLogin, String> {
    // Add custom methods if necessary
}
