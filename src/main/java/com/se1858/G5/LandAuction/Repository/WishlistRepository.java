package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    List<Wishlist> findAllWishlistByUser_UserId(int userId);
}

