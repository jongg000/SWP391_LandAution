package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.RequestDTO;
import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Auction;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@Controller
@RequestMapping("staff")
public class StaffController {
    private AssetRegistrationService assetRegistrationService;
    private UserService userService;
    private StatusService statusService;
    private AuctionService auctionService;
    private EmailService emailService;

    @Autowired
    public StaffController(AssetRegistrationService assetRegistrationService, UserService userService, StatusService statusService, AuctionService auctionService, EmailService emailService) {
        this.assetRegistrationService = assetRegistrationService;
        this.userService = userService;
        this.statusService = statusService;
        this.auctionService = auctionService;
        this.emailService = emailService;
    }

    @GetMapping()
    public String allLandRequest(Model model) {
        long approve = assetRegistrationService.countAssetRegistrationsByStatus(statusService.getStatusById(8));
        long waiting = assetRegistrationService.countAssetRegistrationsByStatus(statusService.getStatusById(4));
        long cancel = assetRegistrationService.countAssetRegistrationsByStatus(statusService.getStatusById(9));
        long auction = auctionService.getTotalAuctions();
        model.addAttribute("approve", approve);
        model.addAttribute("waiting", waiting);
        model.addAttribute("cancel", cancel);
        model.addAttribute("auction", auction);
        return "/staff/home-staff";
    }
    @GetMapping("/land-detail/{id}")
    public String landDetail(@PathVariable("id") int id, Model model) {
        AssetRegistration assetRegistration = assetRegistrationService.getAssetRegistrationByID(id);
        if(assetRegistration.getStatus().getStatusID() == 4 ){
            RequestDTO requestDTO = new RequestDTO();
            requestDTO = requestDTO.convertToDTO(assetRegistration);
            model.addAttribute("requestDTO", requestDTO);
            return "/staff/land-detail-request";
        }else{
            model.addAttribute("approvedAsset", assetRegistration);
            return "/staff/asset-detail";
        }

    }
    @PostMapping("/schedule")
    public String schedule(@ModelAttribute("requestDTO") RequestDTO requestDTO, @RequestParam("action") String action ,Model model, Principal principal) {
        //Lấy session người dung
        User user = userService.findByEmail(principal.getName());
        // Định dạng mẫu ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        // Chuyển đổi từ String sang LocalDateTime
        AssetRegistration assetRegistration = assetRegistrationService.getAssetRegistrationByID(requestDTO.getDocumentId());
        if ("approve".equals(action)) {
            LocalDateTime auctionStat = LocalDateTime.parse(requestDTO.getAuctionStat(), formatter);
            LocalDateTime createdDate = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            assetRegistration.setApprovalDate(createdDate);
            Auction auction = new Auction();
            auction.setStartTime(auctionStat);
            auction.setEndTime(auctionStat.plusMinutes(45));
            auction.setLand(assetRegistration.getLand());
            assetRegistration.setUser(user);
            assetRegistration.setStatus(statusService.getStatusById(8));
            auction.setStatus(statusService.getStatusById(8));
            assetRegistrationService.save(assetRegistration);
            auctionService.saveAuction(auction);
            return "redirect:land-detail/" + requestDTO.getDocumentId();
        } else if ("rejected".equals(action)) {
            LocalDateTime createdDate = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            assetRegistration.setApprovalDate(createdDate);
            assetRegistration.setStatus(statusService.getStatusById(9));
            assetRegistration.setUser(user);
            assetRegistration.setComments(requestDTO.getComments());
            assetRegistrationService.save(assetRegistration);

            return "redirect:land-detail/" + requestDTO.getDocumentId();
        }
        return "staff/home-staff";
    }


    @GetMapping("/handle-request")
    public String handleRequest(Model model) {
        return "land-detail-request";
    }

    @GetMapping("waiting-list")
    public String showWatingList(Model model) {
        List<AssetRegistration> allAssets = assetRegistrationService.findByStatus(statusService.getStatusById(4));
        model.addAttribute("allAssets", allAssets);
        return "/staff/waiting-list";
    }
    @GetMapping("ongoing-list")
    public String showOngoingList(Model model) {
        List<Auction> auctionList = auctionService.getAllAuctionByStartTime();
        if(auctionList.isEmpty()){
            System.out.println("No Auction Found");
        }else{
            System.out.println(auctionList.size());
        }
        model.addAttribute("auctionList", auctionList);
        return "/staff/ongoing-list";
    }
    @GetMapping("cancel-list")
    public String showCancelList(Model model) {
        List<AssetRegistration> allAssets = assetRegistrationService.findByStatus(statusService.getStatusById(9));
        model.addAttribute("allAssets", allAssets);
        return "/staff/cancel-list";
    }
}
