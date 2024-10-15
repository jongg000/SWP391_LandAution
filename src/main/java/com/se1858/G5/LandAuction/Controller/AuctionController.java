package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.AuctionDto;
import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.DTO.LandImageDTO;
import com.se1858.G5.LandAuction.Service.AuctionService;
import com.se1858.G5.LandAuction.Service.LandService;
import com.se1858.G5.LandAuction.Service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private LandService landService;
    @Autowired
    private WishlistService wishlistService;

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

    @GetMapping("/showAuctionDetail/{id}")
    public String showAuctionDetail(@PathVariable int id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        AuctionDto auctionDto = auctionService.findAuctionById(id);
        LandDTO landDTO = landService.findLandById(auctionDto.getLandId());
        List<LandImageDTO> landImageDTO = landService.findAllLandImageByLandId(auctionDto.getLandId());
        boolean check = auctionService.checkExistUserInAuction((int) session.getAttribute("id")   , id);
        boolean wishCheck = wishlistService.checkExistAuctionInWishlist((int) session.getAttribute("id"), id);
        //        String dateCheck;
        //        Duration countdownTime = null;
        //
        //        if (LocalDateTime.now().isBefore(auctionDto.getStartTime())) {
        //            countdownTime = Duration.between(LocalDateTime.now(), auctionDto.getStartTime());
        //            dateCheck = "upcoming";
        //        } else if (LocalDateTime.now().isAfter(auctionDto.getEndTime())) {
        //            dateCheck = "ended";
        //        } else {
        //            dateCheck = "is going on";
        //            countdownTime = Duration.between(LocalDateTime.now(), auctionDto.getEndTime());
        //        }
        model.addAttribute("check",check);
        model.addAttribute("auction", auctionDto);
        model.addAttribute("land", landDTO);
        model.addAttribute("landImages", landImageDTO);
        model.addAttribute("userCheck", check);
        model.addAttribute("wishlistCheck", wishCheck);
        //        model.addAttribute("dateCheck", dateCheck);
        //        model.addAttribute("countdownTime", countdownTime.toMillis());
        return "customer/detailPage";
    }
}

