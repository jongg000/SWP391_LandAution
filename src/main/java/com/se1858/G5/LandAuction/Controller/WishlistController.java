package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.Request.WishlistRequest;
import com.se1858.G5.LandAuction.Service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;


    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }




    @GetMapping("/showWishlists{userId}")
    public String showWishlistPage(Model model, @PathVariable int userId) {
        model.addAttribute("wishlists", wishlistService.findAllWishlistByUserId(userId));
        model.addAttribute("wishlist", new WishlistRequest());
        return "redirect:/wishlistPage";
    }




    @PostMapping("/save")
    public String saveWishlist(@ModelAttribute WishlistRequest wishlistRequest) {
        wishlistService.update(wishlistRequest);
        return "redirect:/wishlistPage";
    }




    @GetMapping("/delete{id}")
    public String deleteAuction(@PathVariable int id) {
        wishlistService.deleteWishlistById(id);
        return "redirect:/wishlistPage";
    }
}

