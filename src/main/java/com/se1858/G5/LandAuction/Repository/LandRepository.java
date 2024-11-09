package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LandRepository extends JpaRepository<Land, Integer> {

    @EntityGraph(attributePaths = {"images"})
    List<Land> findAllByName(String keyword);

    List<Land> findTop4ByOrderByLandIdDesc();

    @Query("SELECT l FROM Land l WHERE " +
            "LOWER(l.province) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.district) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.ward) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Land> searchLandsByKeyword(@Param("keyword") String keyword);
    long countByUser(User user);
    List<Land> findByUser(User user);

    @Query("SELECT l FROM Land l JOIN l.assetRegistration ar WHERE l.user.userId = :userId ORDER BY ar.registrationDate DESC")
    List<Land> findLandsByUserIdOrderedByRegistrationDate(@Param("userId") int userId);

    @Query(value = "SELECT * FROM land l JOIN asset_registration ar ON l.land_id = ar.land_id " +
            "WHERE l.user_user_id = :userId AND ar.statusid = :statusId " +
            "ORDER BY ar.registration_date DESC", nativeQuery = true)
    List<Land> findLandsByUserIdAndStatusId(@Param("userId") int userId,
                                            @Param("statusId") int statusId);

}

