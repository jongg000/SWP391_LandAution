package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.AuctionDto;
import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.DTO.LandImageDTO;
import com.se1858.G5.LandAuction.DTO.WishlistDTO;
import com.se1858.G5.LandAuction.Service.LandService;
import com.se1858.G5.LandAuction.Service.AuctionService;
import com.se1858.G5.LandAuction.Service.ServiceImpl.WishlistServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistServiceImpl wishlistService;
    private final AuctionService auctionService;
    private final LandService landService;


    public WishlistController(WishlistServiceImpl wishlistService, AuctionService auctionService, LandService landService) {
        this.wishlistService = wishlistService;
        this.auctionService = auctionService;
        this.landService = landService;
    }


    @GetMapping("/showWishlists")
    public String showWishlistPage(@RequestParam(defaultValue = "0") int page, Model model) {
        List<WishlistDTO> wishlists = wishlistService.findAllWishlist();
        List<Map<String, Object>> wishlistDetails = new ArrayList<>();
        int pageSize = 4; // Set the page size to 4
        int start = page * pageSize;
        int end = Math.min(start + pageSize, wishlists.size());

        for (int i = start; i < end; i++) {
            WishlistDTO wishlist = wishlists.get(i);
            AuctionDto auction = auctionService.findAuctionById(wishlist.getAuctionId());
            LandDTO land = landService.findLandById(auction.getLandId());
            List<LandImageDTO> landImageDTOList = landService.findAllLandImageByLandId(auction.getLandId());
            LandImageDTO landImage = landImageDTOList.isEmpty() ? null : landImageDTOList.get(0);
            Map<String, Object> details = new HashMap<>();
            details.put("wishlist", wishlist);
            details.put("auction", auction);
            details.put("land", land);
            details.put("landImage", landImage);
            wishlistDetails.add(details);
        }
        model.addAttribute("num", wishlists.size());
        model.addAttribute("wishlistDetails", wishlistDetails);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) wishlists.size() / pageSize)); // Total number of pages

        return "/customer/wishlistPage";
    }


    @GetMapping("/showWishlists/{userId}")
    public String showWishlistPage(@RequestParam(defaultValue = "0") int page, Model model, @PathVariable int userId) {
        List<WishlistDTO> wishlists = wishlistService.findAllWishlistByUserId((int) userId);
        List<Map<String, Object>> wishlistDetails = new ArrayList<>();
        int pageSize = 6;
        int start = page * pageSize;
        int end = Math.min(start + pageSize, wishlists.size());
        int j=0;
        for (int i = start; i < end; i++) {
            WishlistDTO wishlist = wishlists.get(i);
            AuctionDto auction = auctionService.findAuctionById(wishlist.getAuctionId());
            LandDTO land = landService.findLandById(auction.getLandId());
            List<LandImageDTO> landImageDTOList = landService.findAllLandImageByLandId(auction.getLandId());
            LandImageDTO landImage = landImageDTOList.isEmpty() ? null : landImageDTOList.get(0);
            Map<String, Object> details = new HashMap<>();
            String dateCheck = null;
            if(LocalDateTime.now().isAfter(auction.getEndTime())) {
                dateCheck = "ended";
                j++;
            } else if(LocalDateTime.now().isBefore(auction.getStartTime())) {
                dateCheck = "upcoming";
            } else {
                dateCheck = "is going on";
            }

            details.put("dateCheck", dateCheck);
            details.put("wishlist", wishlist);
            details.put("auction", auction);
            details.put("land", land);
            details.put("landImage", landImage);
            wishlistDetails.add(details);
        }
        model.addAttribute("num",wishlistDetails.size());
        model.addAttribute("wishlistDetails", wishlistDetails);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) wishlists.size() / pageSize)); // Tổng số trang

        return "/customer/wishlistPage";
    }





    @GetMapping("/save/{auctionId}")
    public String saveWishlist(@PathVariable int auctionId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("id");
        WishlistDTO wishlistDTO = WishlistDTO.builder()
                .userId((int) session.getAttribute("id"))
                .auctionId(auctionId)
                .build();
        wishlistService.saveWishlist(wishlistDTO);
        return "redirect:/wishlist/showWishlists/" + userId;
    }




    @GetMapping("/delete/{id}")
    public String deleteAuction(@PathVariable String id) {
        int userId = wishlistService.findWishlistById(Integer.valueOf(id)).getUserId();
        wishlistService.deleteWishlistById(id);
        return "redirect:/wishlist/showWishlists/" + userId;
    }
}

