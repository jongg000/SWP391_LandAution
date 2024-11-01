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
import java.util.stream.Collectors;


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


    @GetMapping("/showWishlists/{userId}")
    public String showWishlistPage(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(required = false) String filter,
                                   Model model,
                                   @PathVariable int userId) {
        List<WishlistDTO> wishlists = wishlistService.findAllWishlistByUserId(userId);
        List<WishlistDTO> filteredWishlists;
        if (filter != null && !filter.equals("all")) {
            filteredWishlists = wishlists.stream()
                    .filter(wishlist -> {
                        AuctionDto auction = auctionService.findAuctionById(wishlist.getAuctionId());
                        LocalDateTime now = LocalDateTime.now();
                        String dateCheck;

                        // Determine the status of the auction
                        if (now.isAfter(auction.getEndTime())) {
                            dateCheck = "ended";
                        } else if (now.isBefore(auction.getStartTime())) {
                            dateCheck = "comingSoon";
                        } else {
                            dateCheck = "isGoingOn";
                        }
                        switch (filter) {
                            case "ended":
                                return "ended".equals(dateCheck);
                            case "isGoingOn":
                                return "isGoingOn".equals(dateCheck);
                            case "comingSoon":
                                return "comingSoon".equals(dateCheck);
                            default:
                                return true; // In case of an unexpected filter value, show all
                        }
                    })
                    .collect(Collectors.toList());
        } else {
            // If no filter is applied, show all wishlists
            filteredWishlists = wishlists;
        }

        int pageSize = 6; // Change as necessary
        int totalFilteredWishlists = filteredWishlists.size();
        int start = page * pageSize;
        int end = Math.min(start + pageSize, totalFilteredWishlists);

        List<Map<String, Object>> wishlistDetails = new ArrayList<>();
        for (int i = start; i < end; i++) {
            WishlistDTO wishlist = filteredWishlists.get(i);
            AuctionDto auction = auctionService.findAuctionById(wishlist.getAuctionId());
            LandDTO land = landService.findLandById(auction.getLandId());
            List<LandImageDTO> landImageDTOList = landService.findAllLandImageByLandId(auction.getLandId());
            LandImageDTO landImage = landImageDTOList.isEmpty() ? null : landImageDTOList.get(0);
            Map<String, Object> details = new HashMap<>();

            String dateCheck;
            if (LocalDateTime.now().isAfter(auction.getEndTime())) {
                dateCheck = "ended";
            } else if (LocalDateTime.now().isBefore(auction.getStartTime())) {
                dateCheck = "comingSoon";
            } else {
                dateCheck = "isGoingOn";
            }

            details.put("dateCheck", dateCheck);
            details.put("wishlist", wishlist);
            details.put("auction", auction);
            details.put("land", land);
            details.put("landImage", landImage);
            wishlistDetails.add(details);
        }
        model.addAttribute("num", totalFilteredWishlists); // Number of filtered wishlists
        model.addAttribute("wishlistDetails", wishlistDetails);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalFilteredWishlists / pageSize)); // Total pages based on filtered list
        model.addAttribute("currentFilter", filter); // Maintain the current filter for the view

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

