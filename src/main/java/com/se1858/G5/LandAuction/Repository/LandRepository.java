package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Land;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;

public interface LandRepository extends JpaRepository<Land, Integer> {
    @Query(value = "SELECT a.auction_id, a.statusid, a.start_time, a.end_time, " +
            "l.land_id, CAST(l.description AS VARCHAR(MAX)) AS description, " +
            "l.price, CAST(l.location AS VARCHAR(MAX)) AS location, " +
            "(SELECT STRING_AGG(li.image_url, ', ') FROM land_image li WHERE li.land_land_id = l.land_id) AS imageUrls, " +
            "(SELECT STRING_AGG(li.name, ', ') FROM land_image li WHERE li.land_land_id = l.land_id) AS imageNames, " +
            "l.name as name " +
            "FROM auction a " +
            "JOIN land l ON a.landid = l.land_id " +
            "WHERE a.start_time = (SELECT MAX(au.start_time) FROM auction au WHERE au.landid = l.land_id)",
            nativeQuery = true)
    Page<Tuple> findAuctionDetailsWithImages(Pageable pageable);
}

