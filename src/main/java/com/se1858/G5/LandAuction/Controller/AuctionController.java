package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.AuctionDto;
import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.DTO.LandImageDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private LandService landService;

    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private AuctionRegistrationService auctionRegistrationService;
    @Autowired
    private UserService userService;
    @Autowired
    private AssetRegistrationService assetRegistrationService;
    @Autowired
    private BidService bidService;

    @GetMapping
    public String showAuctionPage(Model model) {
        model.addAttribute("auctions", auctionService.getAllAuctions());
        model.addAttribute("auction", new AuctionDto());
        return "Manager/Auction";
    }

    @PostMapping("/save")
    public String saveAuction(@ModelAttribute AuctionDto auctionDto) {
        auctionService.update(auctionDto);
        return "redirect:/auction";
    }

    @GetMapping("/edit{id}")
    public String editAuction(@PathVariable int id, Model model) {
        model.addAttribute("auction", auctionService.findAuctionById(id));
        model.addAttribute("auctions", auctionService.getAllAuctions());
        return "Manager/Auction";
    }

    @GetMapping("/delete{id}")
    public String deleteAuction(@PathVariable int id) {
        auctionService.deleteAuction(id);
        return "redirect:/auction";
    }

    @GetMapping("/showAuctions")
    public String showAuctions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            Model model) {

        List<AuctionDto> allAuctions = auctionService.getAllAuctions();
        int totalAuctions = allAuctions.size();
        int totalPages = (int) Math.ceil((double) totalAuctions / size);

        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalAuctions);
        if (fromIndex >= totalAuctions || fromIndex < 0) {
            page = 0;
            fromIndex = 0;
            toIndex = Math.min(size, totalAuctions);
        }

        List<AuctionDto> paginatedAuctions = allAuctions.subList(fromIndex, toIndex);
        List<Map<String, Object>> auctionDetails = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (AuctionDto auction : paginatedAuctions) {
            Map<String, Object> details = new HashMap<>();
            LandDTO land = landService.findLandById(auction.getLandId());
            List<LandImageDTO> landImageList = landService.findAllLandImageByLandId(auction.getLandId());
            details.put("auction", auction);
            details.put("land", land);
            details.put("Image", landImageList.isEmpty() ? null : landImageList.get(0).getImageUrl());

            // Determine the status of the auction
            String dateCheck;
            if (now.isAfter(auction.getEndTime())) {
                dateCheck = "ended";
            } else if (now.isBefore(auction.getStartTime())) {
                dateCheck = "comingSoon";
            } else {
                dateCheck = "isGoingOn";
            }
            details.put("dateCheck", dateCheck); // Add the dateCheck status

            auctionDetails.add(details);
        }

        model.addAttribute("auctionDetails", auctionDetails);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "auctionPage";
    }
    @GetMapping("/showAuctionResults")
    @Transactional(readOnly = true)
    public String showAuctionResults(Model model) {
        List<Auction> auctions = auctionService.findAllAuctionEnd();
        List<Map<String, Object>> auctionDetails = getAuctionDetailsList(auctions);

        model.addAttribute("auctionDetails", auctionDetails);
        return "staff/home-staff";
    }

    private List<Map<String, Object>> getAuctionDetailsList(List<Auction> auctions) {
        List<Map<String, Object>> auctionDetailsList = new ArrayList<>();
        for (Auction auction : auctions) {
            auctionDetailsList.add(createAuctionDetailMap(auction));
        }
        return auctionDetailsList;
    }

    private Map<String, Object> createAuctionDetailMap(Auction auction) {
        Map<String, Object> details = new HashMap<>();
        Bids bids = bidService.findBidByAuctionAndBidAmount(auction, auction.getHighestBid());
        AuctionRegistration auctionRegistration = bids != null ? bids.getAuctionRegistration() : null;
        User bidder = (auctionRegistration != null) ? auctionRegistration.getUser() : null;
        LandDTO land = landService.findLandById(auction.getLand().getLandId());
        List<LandImageDTO> landImageList = landService.findAllLandImageByLandId(auction.getLand().getLandId());
        String imageUrl = (!landImageList.isEmpty()) ? landImageList.get(0).getImageUrl() : null;
        details.put("auction", auction);
        details.put("land", land);
        details.put("Image", imageUrl);
        details.put("highestBid", auction.getHighestBid());
        details.put("bidder", bidder);

        return details;
    }
    @GetMapping("/showAuctionDetail/{id}")
    public String showAuctionDetail(@PathVariable int id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        AuctionDto auctionDto = auctionService.findAuctionById(id);
        LandDTO landDTO = landService.findLandById(auctionDto.getLandId());
        List<LandImageDTO> landImageDTO = landService.findAllLandImageByLandId(auctionDto.getLandId());
        boolean check = auctionService.checkExistUserInAuction((int) session.getAttribute("id")   , id);
        boolean wishCheck = wishlistService.checkExistAuctionInWishlist((int) session.getAttribute("id"), id);
        boolean isAvailable = auctionRegistrationService.checkAvailableAttend((int) session.getAttribute("id"));
        boolean isSeller = auctionRegistrationService.checkAvailableAttend((int) session.getAttribute("id"), auctionDto.getLandId());
        String dateCheck = null;
        if(LocalDateTime.now().isAfter(auctionDto.getEndTime())) {
            dateCheck = "ended";
        } else if(LocalDateTime.now().isBefore(auctionDto.getStartTime())) {
            dateCheck = "upcoming";
        } else {
            dateCheck = "is going on";
        }
        List<AuctionDto> auctionDtos = auctionService.getTop4NewestAuctions();
        List<Map<String, Object>> auctionDetails = new ArrayList<>();
        for (AuctionDto a : auctionDtos) {
            Map<String, Object> details = new HashMap<>();
            LandDTO l = landService.findLandById(a.getLandId());
            List<LandImageDTO> landImageList = landService.findAllLandImageByLandId(a.getLandId());
            details.put("auction", a);
            details.put("land", l);
            details.put("Image", landImageList.get(0).getImageUrl());
            auctionDetails.add(details);
        }
        model.addAttribute("auctionDetails", auctionDetails);
        model.addAttribute("isAvailable",isAvailable);
        model.addAttribute("isSeller",isSeller);
        model.addAttribute("check",check);
        model.addAttribute("auction", auctionDto);
        model.addAttribute("land", landDTO);
        model.addAttribute("landImages", landImageDTO);
        model.addAttribute("userCheck", check);
        model.addAttribute("wishlistCheck", wishCheck);
        model.addAttribute("dateCheck", dateCheck);
        return "auctionDetailPage";
    }

}

