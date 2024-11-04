package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.*;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    @RequestMapping("/showAuctionRegistrationPage")
    public String showAuctionRegistrationPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String filter, // Add filter parameter
            Model model,
            HttpServletRequest request) {

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("id");
        List<AuctionRegistrationDTO> auctionRegistrationDTOS = auctionRegistrationService.getAllAuctionRegistrationsByUserId(userId);

        if (filter != null && !filter.equals("all")) {
            auctionRegistrationDTOS = auctionRegistrationDTOS.stream()
                    .filter(auctionRegistration -> {
                        AuctionDto auction = auctionService.findAuctionById(auctionRegistration.getAuctionId());
                        String dateCheck;
                        if (LocalDateTime.now().isAfter(auction.getEndTime())) {
                            dateCheck = "ended";
                        } else if (LocalDateTime.now().isBefore(auction.getStartTime())) {
                            dateCheck = "upcoming";
                        } else {
                            dateCheck = "is going on";
                        }
                        return dateCheck.equals(filter);
                    })
                    .collect(Collectors.toList());
        }

        List<Map<String, Object>> auctionRegistrationDetails = new ArrayList<>();
        int pageSize = 10;
        int start = page * pageSize;
        int end = Math.min(start + pageSize, auctionRegistrationDTOS.size());

        for (int i = start; i < end; i++) {
            AuctionRegistrationDTO auctionRegistrationDTO = auctionRegistrationDTOS.get(i);
            AuctionDto auction = auctionService.findAuctionById(auctionRegistrationDTO.getAuctionId());
            LandDTO land = landService.findLandById(auction.getLandId());
            List<LandImageDTO> landImageDTOList = landService.findAllLandImageByLandId(auction.getLandId());
            LandImageDTO landImage = landImageDTOList.isEmpty() ? null : landImageDTOList.get(0);
            Map<String, Object> details = new HashMap<>();

            String dateCheck;
            if(LocalDateTime.now().isAfter(auction.getEndTime())) {
                dateCheck = "ended";
            } else if(LocalDateTime.now().isBefore(auction.getStartTime())) {
                dateCheck = "upcoming";
            } else {
                dateCheck = "is going on";
            }

            details.put("dateCheck", dateCheck);
            details.put("wishlist", auctionRegistrationDTO);
            details.put("auction", auction);
            details.put("land", land);
            details.put("landImage", landImage);
            auctionRegistrationDetails.add(details);
        }

        model.addAttribute("num", auctionRegistrationDTOS.size());
        model.addAttribute("wishlistDetails", auctionRegistrationDetails);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) auctionRegistrationDTOS.size() / pageSize));
        model.addAttribute("currentFilter", filter);
        return "customer/listAuctionRegister";
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
