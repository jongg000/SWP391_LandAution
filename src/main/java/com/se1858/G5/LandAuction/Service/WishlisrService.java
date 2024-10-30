package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.WishlistDTO;
import com.se1858.G5.LandAuction.Entity.Wishlist;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Repository.WishlistRepository;

import java.util.List;

public interface WishlisrService {
    public boolean checkExistAuctionInWishlist(int userId, int auctionId);
    public void saveWishlist(WishlistDTO wishlistRequest);
    public List<WishlistDTO> findAllWishlistByUserId(int userId);
    public WishlistDTO findWishlistById(int wishlistId);
    public List<WishlistDTO> findAllWishlist();
    public void deleteWishlistById(String wishlistId);
    public WishlistDTO update(WishlistDTO wishlistRequest);

}
