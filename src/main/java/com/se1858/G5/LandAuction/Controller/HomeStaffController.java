package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class HomeStaffController {

    private final AssetRegistrationService assetRegistrationService;
    public HomeStaffController(AssetRegistrationService assetRegistrationService) {
        this.assetRegistrationService = assetRegistrationService;
    }
    @GetMapping()
    public String homePage(Model model) {

        List<AssetRegistration> assetRegistrations = assetRegistrationService.getAssetRegistrations();
        model.addAttribute("assetRegistration", assetRegistrations);
        return "staff/home-staff";
    }
}
