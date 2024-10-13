package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.WishlistDTO;
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



    @GetMapping("/showWishlists")
    public String showWishlistPage(Model model) {
        model.addAttribute("wishlists", wishlistService.findAllWishlist());
        model.addAttribute("wishlist", new WishlistDTO());
        return "Customer/wishlistPage";
    }
    @GetMapping("/showWishlists/{userId}")
    public String showWishlistPage(Model model, @PathVariable int userId) {
        model.addAttribute("wishlists", wishlistService.findAllWishlistByUserId(userId));
        model.addAttribute("wishlist", new WishlistDTO());
        return "Customer/wishlistPage";
    }




    @PostMapping("/save")
    public String saveWishlist(@ModelAttribute WishlistDTO wishlistRequest) {
        wishlistService.update(wishlistRequest);
        return "redirect:/wishlistPage";
    }




    @GetMapping("/delete/{id}")
    public String deleteAuction(@PathVariable String id) {
        int userId = wishlistService.findWishlistById(Integer.valueOf(id)).getUserId();
        wishlistService.deleteWishlistById(id);
        return "redirect:/wishlist/showWishlists/" + userId;
    }
}

