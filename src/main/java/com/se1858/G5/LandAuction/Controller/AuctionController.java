package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.DTO.Request.AuctionRequest;
import com.se1858.G5.LandAuction.Service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping ("/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping
    public String showAuctionPage(Model model) {
        model.addAttribute("auctions", auctionService.getAllAuctions());
        model.addAttribute("auction", new AuctionRequest());
        return "Manager/Auction";
    }

    @PostMapping("/save")
    public String saveAuction(@ModelAttribute AuctionRequest auctionRequest) {
        auctionService.update(auctionRequest);
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
}
