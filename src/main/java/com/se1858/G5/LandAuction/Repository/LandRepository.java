package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LandRepository extends JpaRepository<Land, Integer> {

    @EntityGraph(attributePaths = {"images"})
    List<Land> findAllByName(String keyword);
    List<Land> findByUser(User user);
}

