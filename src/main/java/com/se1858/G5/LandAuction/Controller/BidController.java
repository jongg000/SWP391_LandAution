package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.*;
import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Entity.AuctionRegistration;
import com.se1858.G5.LandAuction.Entity.Bids;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.AuctionRegistrationRepository;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Service.AuctionRegistrationService;
import com.se1858.G5.LandAuction.Service.BidService;
import com.se1858.G5.LandAuction.Service.LandService;
import com.se1858.G5.LandAuction.Service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/bids")
public class BidController {
    @Autowired
    private BidService bidService;
    @Autowired
    private AuctionRegistrationService auctionRegisterService;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private LandService landService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuctionRepository auctionRepository;
    @Autowired
    private AuctionRegistrationRepository auctionRegistrationRepository;
    @Autowired
    private AuctionRegistrationService auctionRegistrationService;

    @GetMapping("/showBidsPage/{auctionId}")
    public String showBidsPage(Model model, @PathVariable int auctionId, HttpServletRequest request) {
        List<BidDTO> bids = bidService.getAllBids(auctionId);
        AuctionDto auctionDto = auctionService.findAuctionById(auctionId);


        BidDTO bidDTO = bids.isEmpty() ? null : bids.get(0);

        long latestBidAmount = bidDTO != null ? bidDTO.getBidAmount() : 0;
        int latestBidUserId = bidDTO != null ? auctionRegistrationService.getById(bidDTO.getRegistrationId()).getUserId() : -1;

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("id");

        LandDTO landDTO = landService.findLandById(auctionDto.getLandId());
        LandImageDTO landImageDTO = landService.findAllLandImageByLandId(auctionDto.getLandId()).get(0);

        List<Map<String, Object>> bidDetails = new ArrayList<>();
        for (BidDTO bid : bids) {
            AuctionRegistrationDTO auctionRegistrationDTO1 = auctionRegisterService.getById(bid.getRegistrationId());
            String username = userRepository.getById(auctionRegistrationDTO1.getUserId()).getLastName();
            Map<String, Object> details = new HashMap<>();
            details.put("bidId", bid.getRegistrationId());
            details.put("bid", bid);
            details.put("username", username);
            bidDetails.add(details);
        }
        boolean checkWinner = auctionService.checkWinner(auctionId, userId);
        Auction auction1 = auctionRepository.findByAuctionId(auctionId);

        User user = userRepository.findById(userId).orElse(null);
        String userName = user != null ? user.getLastName() : "Unknown"+userId;
        if(auction1.getStatus().getStatusID()==11||auction1.getStatus().getStatusID()==13){
            Bids bids1 = bidService.findBidByAuctionAndBidAmount(auction1, auction1.getHighestBid());
            AuctionRegistration auctionRegistration = bids1 != null ? bids1.getAuctionRegistration() : null;
            User bidder = auctionRegistration.getUser();
            userName = bidder.getLastName();
        }

        model.addAttribute("checkWinner", checkWinner);
        model.addAttribute("auction1", auction1.getStatus().getStatusID());
        model.addAttribute("userName", userName);
        model.addAttribute("userId", userId);
        model.addAttribute("latestBidUserId", latestBidUserId);
        model.addAttribute("latestBidAmount", latestBidAmount);
        AuctionRegistrationDTO auctionRegistration = auctionRegisterService.getByUser_UserIdAndAuction_AuctionId(userId, auctionId);
        if (auctionRegistration != null) {
            model.addAttribute("auctionRegistrationId", auctionRegistration.getRegistrationId());
        } else {
            model.addAttribute("auctionRegistrationId", null);
        }
        model.addAttribute("auction", auctionDto);
        model.addAttribute("land", landDTO);
        model.addAttribute("landImages", landImageDTO);
        model.addAttribute("bidDetails", bidDetails);
        return "customer/bidPage";
    }

    @PostMapping("/save/{auctionId}")
    public String saveBids(@RequestParam long bidAmount, @PathVariable int auctionId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("id");
        AuctionRegistrationDTO auctionRegistrationDTO = auctionRegisterService.getByUser_UserIdAndAuction_AuctionId(userId, auctionId);
        BidDTO bidDTO = BidDTO.builder()
                .bidAmount(bidAmount)
                .registrationId(auctionRegistrationDTO.getRegistrationId())
                .bidTime(LocalDateTime.now())
                .build();

        bidService.saveBid(bidDTO);
        return "redirect:/bids/showBidsPage/" + auctionId;
    }

}