package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.LandImageDTO;
import com.se1858.G5.LandAuction.DTO.RequestDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.AuctionRepository;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Service.*;
import com.se1858.G5.LandAuction.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Controller
@RequestMapping("staff")
public class StaffController {
    private AssetRegistrationService assetRegistrationService;
    private UserService userService;
    private StatusService statusService;
    private AuctionService auctionService;
    private AuctionRepository auctionRepository;
    private LandService landService;
    private BidService bidService;
    private LandRepository landRepository;
    private EmailService emailService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public StaffController(AssetRegistrationService assetRegistrationService, UserService userService, StatusService statusService, AuctionService auctionService, AuctionRepository auctionRepository, LandService landService, BidService bidService, LandRepository landRepository) {
        this.assetRegistrationService = assetRegistrationService;
        this.userService = userService;
        this.statusService = statusService;
        this.auctionService = auctionService;
        this.auctionRepository = auctionRepository;
        this.landService = landService;

        this.bidService = bidService;
        this.landRepository = landRepository;
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
            assetRegistration.setStatus(statusService.getStatusById(10));
            auction.setStatus(statusService.getStatusById(10));
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

    @GetMapping("/showAuctionResults")
    public String showAuctionResults(Model model) {
        List<Auction> auctions = auctionRepository.findAll();
        List<Map<String, Object>> auctionDetails = getAuctionDetailsList(auctions);
        model.addAttribute("auctionDetails", auctionDetails);
        return "staff/auction-list";
    }

    @GetMapping("/showStaffAuctionDetail/{id}")
    public String showAuctionStaffDetail(Model model,@PathVariable int id) {
        Auction auction = auctionRepository.findByAuctionId(id);
        Map<String, Object> details = new HashMap<>();
        Bids bids = bidService.findBidByAuctionAndBidAmount(auction, auction.getHighestBid());
        AuctionRegistration auctionRegistration = bids != null ? bids.getAuctionRegistration() : null;
        User bidder = (auctionRegistration != null && (auction.getStatus().getStatusID() == 11 || auction.getStatus().getStatusID() == 13)) ? auctionRegistration.getUser() : null;
        Land land = landRepository.findById(auction.getLand().getLandId()).orElse(null);
        List<LandImageDTO> landImageList = landService.findAllLandImageByLandId(auction.getLand().getLandId());
        String imageUrl = (!landImageList.isEmpty()) ? landImageList.get(0).getImageUrl() : null;
        details.put("status", auction.getStatus());
        details.put("auction", auction);
        details.put("land", land);
        details.put("Image", imageUrl);
        details.put("highestBid", auction.getHighestBid());
        details.put("bidder", bidder);
        model.addAttribute("detail", details);
        return "staff/auction-detail";
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
        User bidder = (auctionRegistration != null && (auction.getStatus().getStatusID() == 11 || auction.getStatus().getStatusID() == 13)) ? auctionRegistration.getUser() : null;
        Land land = landRepository.findById(auction.getLand().getLandId()).orElse(null);
        List<LandImageDTO> landImageList = landService.findAllLandImageByLandId(auction.getLand().getLandId());
        String imageUrl = (!landImageList.isEmpty()) ? landImageList.get(0).getImageUrl() : null;
        details.put("status", auction.getStatus());
        details.put("auction", auction);
        details.put("land", land);
        details.put("Image", imageUrl);
        details.put("highestBid", auction.getHighestBid());
        details.put("bidder", bidder);


        return details;
    }
    @GetMapping("/handle-request")
    public String handleRequest(Model model) {

        return "/staff/land-detail-request";
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
    @GetMapping("cancel-listing")
    public String showCancelList(Model model) {
        List<AssetRegistration> allAssets = assetRegistrationService.findByStatus(statusService.getStatusById(9));
        model.addAttribute("allAssets", allAssets);
        return "/staff/cancel-list";
    }

    @GetMapping("profile")
    public String showProfile(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        model.addAttribute("user", user);
        return "/staff/staff-profile";
    }

    @PostMapping("/upload")
    public String upAvatar(@RequestParam("avatar") MultipartFile images,
                           RedirectAttributes redirectAttributes, Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        UploadFile uploadFile = new UploadFile();
        uploadFile.upLoadDocumentAvata(images, user);
        userService.save(user);
        model.addAttribute("user", user);
        return "redirect:/customer-care/profile";
    }

    @GetMapping("/change")
    public String showChangePasswordForm(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Đưa thông tin người dùng vào model (nếu cần)
        model.addAttribute("user", user);

        return "staff/change";
    }


    @PostMapping("/change")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Kiểm tra mật khẩu hiện tại có đúng không
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng.");
            model.addAttribute("user", user);
            return "customer-care/change";
        }

        // Kiểm tra nếu mật khẩu mới giống với mật khẩu hiện tại
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu mới không được giống mật khẩu hiện tại.");
            model.addAttribute("user", user);
            return "customer-care/change";
        }

        // Kiểm tra xem mật khẩu mới có khớp với xác nhận mật khẩu không
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
            model.addAttribute("user", user);
            return "customer-care/change";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);

        model.addAttribute("success", "Mật khẩu đã được cập nhập.");
        model.addAttribute("user", user);
        return "staff/change";
    }

}
