package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.AuctionDto;
import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.DTO.WishlistDTO;
import com.se1858.G5.LandAuction.Service.AuctionService;
import com.se1858.G5.LandAuction.Service.LandService;
import com.se1858.G5.LandAuction.Service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;
    private final AuctionService auctionService;
    private final LandService landService;


    public WishlistController(WishlistService wishlistService, AuctionService auctionService, LandService landService) {
        this.wishlistService = wishlistService;
        this.auctionService = auctionService;
        this.landService = landService;
    }


    @GetMapping("/showWishlists")
    public String showWishlistPage(Model model) {
        List<WishlistDTO> wishlists = wishlistService.findAllWishlist();
        List<Map<String, Object>> wishlistDetails = new ArrayList<>();
        for (WishlistDTO wishlist : wishlists) {
            AuctionDto auction = auctionService.findAuctionById(wishlist.getAuctionId());
            LandDTO land = landService.findLandById(auction.getLandId());
            Map<String, Object> details = new HashMap<>();
            details.put("wishlist", wishlist);
            details.put("auction", auction);
            details.put("land", land);
            wishlistDetails.add(details);
        }
        model.addAttribute("wishlistDetails", wishlistDetails);
        return "customer/wishlistPage";
    }
    @GetMapping("/showWishlists/{userId}")
    public String showWishlistPage(Model model, @PathVariable int userId) {
        List<WishlistDTO> wishlists = wishlistService.findAllWishlistByUserId(userId);
        List<Map<String, Object>> wishlistDetails = new ArrayList<>();
        for (WishlistDTO wishlist : wishlists) {
            AuctionDto auction = auctionService.findAuctionById(wishlist.getAuctionId());
            LandDTO land = landService.findLandById(auction.getLandId());
            Map<String, Object> details = new HashMap<>();
            details.put("wishlist", wishlist);
            details.put("auction", auction);
            details.put("land", land);
            wishlistDetails.add(details);
        }
        model.addAttribute("wishlistDetails", wishlistDetails);
        return "customer/wishlistPage";
    }





    @GetMapping("/save/{auctionId}")
    public String saveWishlist(@PathVariable int auctionId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        WishlistDTO wishlistDTO = WishlistDTO.builder()
                .userId((int) session.getAttribute("id"))
                .auctionId(auctionId)
                .build();
        wishlistService.saveWishlist(wishlistDTO);
        return "redirect:/wishlist/showWishlists";
    }




    @GetMapping("/delete/{id}")
    public String deleteAuction(@PathVariable String id) {
        int userId = wishlistService.findWishlistById(Integer.valueOf(id)).getUserId();
        wishlistService.deleteWishlistById(id);
        return "redirect:/wishlist/showWishlists/" + userId;
    }
}

