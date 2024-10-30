package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class StaffValidateProfileController {
    @Autowired
    private AssetRegistrationService assetRegistrationService;
    @Autowired
    private UserService userService;
}
