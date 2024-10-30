package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.WishlistDTO;
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



    public boolean checkExistAuctionInWishlist(int userId, int auctionId) {
        return wishlistRepository.existsByUser_UserIdAndAuction_AuctionId(userId, auctionId);
    }

    public void saveWishlist(WishlistDTO wishlistRequest) {
        Wishlist wishlist = convertToEntity(wishlistRequest);
        wishlistRepository.save(wishlist);
    }




    private WishlistDTO convertToDTO(Wishlist wishlist) {
        return WishlistDTO.builder()
                .wishlistId(wishlist.getWishlistId())
                .userId(wishlist.getUser().getUserId())
                .auctionId(wishlist.getAuction().getAuctionId())
                .build();
    }




    private Wishlist convertToEntity(WishlistDTO wishlistRequest) {
        return Wishlist.builder()
                .wishlistId(wishlistRequest.getWishlistId())
                .user(this.userRepository.findById(wishlistRequest.getUserId()).orElse(null))
                .auction(this.auctionRepository.findById(wishlistRequest.getAuctionId()).orElse(null))
                .build();
    }


    public List<WishlistDTO> findAllWishlistByUserId(int userId) {
        return wishlistRepository.findAllByUser(userRepository.findById(userId).orElse(null)).
                stream().map(this::convertToDTO).
                collect(Collectors.toList());
    }




    public WishlistDTO findWishlistById(int wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .map(this::convertToDTO)
                .orElse(null);
    }


    public List<WishlistDTO> findAllWishlist() {
        return wishlistRepository.findAll().
                stream().map(this::convertToDTO).
                collect(Collectors.toList());
    }

    public void deleteWishlistById(String wishlistId) {
        wishlistRepository.deleteById(Integer.valueOf(wishlistId));
    }


    public WishlistDTO update(WishlistDTO wishlistRequest) {
        Wishlist wishlist = convertToEntity(wishlistRequest);
        Wishlist udWishlist = wishlistRepository.save(wishlist);
        return convertToDTO(udWishlist);
    }
}


