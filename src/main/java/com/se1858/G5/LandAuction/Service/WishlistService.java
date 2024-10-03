package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.Request.WishlistRequest;
import com.se1858.G5.LandAuction.Entity.Wishlist;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;




    @Autowired
    public WishlistService(WishlistRepository wishlistRepository,
                           UserRepository userRepository,
                           AuctionRepository auctionRepository) {
        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
    }




    public void saveWishlist(WishlistRequest wishlistRequest) {
        Wishlist wishlist = convertToEntity(wishlistRequest);
        wishlistRepository.save(wishlist);
    }




    private WishlistRequest convertToDTO(Wishlist wishlist) {
        return WishlistRequest.builder()
                .wishlistId(wishlist.getWishlistId())
                .userId(wishlist.getUser().getUserId())
                .auctionId(wishlist.getAuction().getAuctionId())
                .build();
    }




    private Wishlist convertToEntity(WishlistRequest wishlistRequest) {
        return Wishlist.builder()
                .wishlistId(wishlistRequest.getWishlistId())
                .user(this.userRepository.findById(wishlistRequest.getUserId()).orElse(null))
                .auction(this.auctionRepository.findById(wishlistRequest.getAuctionId()).orElse(null))
                .build();
    }


    public List<WishlistRequest> findAllWishlistByUserId(int userId) {
        return wishlistRepository.findAllWishlistByUser_UserId(userId).
                stream().map(this::convertToDTO).
                collect(Collectors.toList());
    }




    public WishlistRequest findWishlistById(int wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .map(this::convertToDTO)
                .orElse(null);
    }




    public void deleteWishlistById(int wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }




    public WishlistRequest update(WishlistRequest wishlistRequest) {
        Wishlist wishlist = convertToEntity(wishlistRequest);
        Wishlist udWishlist = wishlistRepository.save(wishlist);
        return convertToDTO(udWishlist);
    }
}


