package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.*;
import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import com.se1858.G5.LandAuction.Service.AuctionRegistrationService;
import com.se1858.G5.LandAuction.Service.LandService;
import com.se1858.G5.LandAuction.Service.UserService;
import com.se1858.G5.LandAuction.Service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auctionRegistration")
public class AuctionRegistrationController {
    @Autowired
    private AuctionRegistrationService auctionRegistrationService;
    @Autowired
    private AuctionService auctionService;
    UserService userService;
    @Autowired
    private LandService landService;
    @Autowired
    private AuctionRepository auctionRepository;


    @RequestMapping("/showAuctionRegistrationPage")
    public String showAuctionRegistrationPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String filter,
            Model model,
            HttpServletRequest request) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("id");
        List<AuctionRegistrationDTO> auctionRegistrationDTOS = auctionRegistrationService.getAllAuctionRegistrationsByUserId(userId);
        List<AuctionRegistrationDTO> filteredWishlists;

        if (filter != null && !filter.equals("all")) {
            filteredWishlists = auctionRegistrationDTOS.stream()
                    .filter(wishlist -> {
                        AuctionDto auction = auctionService.findAuctionById(wishlist.getAuctionId());
                        LocalDateTime now = LocalDateTime.now();
                        String dateCheck;

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
            filteredWishlists = auctionRegistrationDTOS;
        }

        int pageSize = 10; // Change as necessary
        int totalFilteredWishlists = filteredWishlists.size();
        int start = page * pageSize;
        int end = Math.min(start + pageSize, totalFilteredWishlists);

        List<Map<String, Object>> wishlistDetails = new ArrayList<>();
        for (int i = start; i < end; i++) {
            AuctionRegistrationDTO wishlist = filteredWishlists.get(i);
            AuctionDto auction = auctionService.findAuctionById(wishlist.getAuctionId());
            LandDTO land = landService.findLandById(auction.getLandId());
            String formattedPrice = numberFormat.format(land.getPrice());
            String formattedStartTime = auction.getStartTime().format(dateTimeFormatter);
            String formattedEndTime = auction.getEndTime().format(dateTimeFormatter);
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
            boolean checkWinner = auctionService.checkWinner(auction.getAuctionId(), userId);
            Auction auction1 = auctionRepository.findByAuctionId(auction.getAuctionId());
            details.put("auction1", auction1.getStatus().getStatusID());
            details.put("checkWinner", checkWinner);
            details.put("dateCheck", dateCheck);
            details.put("wishlist", wishlist);
            details.put("auction", auction);
            details.put("land", land);
            details.put("landImage", landImage);
            details.put("formattedPrice", formattedPrice);
            details.put("formattedStartTime", formattedStartTime);
            details.put("formattedEndTime", formattedEndTime);
            wishlistDetails.add(details);
        }
        model.addAttribute("num", totalFilteredWishlists); // Number of filtered wishlists
        model.addAttribute("wishlistDetails", wishlistDetails);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalFilteredWishlists / pageSize)); // Total pages based on filtered list
        model.addAttribute("currentFilter", filter);
        return "/customer/listAuctionRegister";
    }
    @RequestMapping("/save/{id}")
    public String saveAuctionRegistration(@PathVariable int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("id");
        AuctionRegistrationDTO wishlistDTO = AuctionRegistrationDTO.builder()
                .userId(userId)
                .auctionId(id)
                .build();
        auctionRegistrationService.save(wishlistDTO);
        return "redirect:/auctionRegistration/showAuctionRegistrationPage";
    }
}
