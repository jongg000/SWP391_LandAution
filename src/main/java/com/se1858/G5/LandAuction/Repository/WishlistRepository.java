package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    boolean existsByUser_UserIdAndAuction_AuctionId(int userId, int auctionId);
    List<Wishlist> findAllByUser(User user);
}

