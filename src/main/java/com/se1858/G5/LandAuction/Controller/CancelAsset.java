package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("cancel")
public class CancelAsset {
    private AssetRegistrationService assetRegistrationService;

    public CancelAsset(AssetRegistrationService assetRegistrationService) {
        this.assetRegistrationService = assetRegistrationService;
    }

    @GetMapping()
    public String cancel() {

        return "cancel-form";
    }

    @PostMapping()
    public String cancelAsset() {

        return "/customer/cancel-form";
    }
}
