package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import com.se1858.G5.LandAuction.Service.StatusService;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/asset-request")
public class StaffValidateLandController {
    @Autowired
    private AssetRegistrationService assetRegistrationService;
    @Autowired
    private UserService userService;
    @Autowired
    private StatusService statusService;
    @GetMapping("/haha")
    public String allLandRequest(Model model) {
        List<AssetRegistration> allAssets = assetRegistrationService.findByStatus(statusService.getStatusById(4));
        model.addAttribute("allAssets", allAssets);
        return "/staff/land-request";
    }
    @GetMapping("/land-detail/{id}")
    public String landDetail(@PathVariable int id, Model model) {
        AssetRegistration assetRegistration = assetRegistrationService.getAssetRegistrationByID(id);
        model.addAttribute("assetRegistration", assetRegistration);
        return "/staff/land-detail";
    }
    @GetMapping("handle-request")
    public String handleRequest(Model model) {
        return "/staff/land-detail";
    }
}
