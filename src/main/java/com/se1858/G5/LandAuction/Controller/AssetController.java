
package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import com.se1858.G5.LandAuction.Service.LandService;
import com.se1858.G5.LandAuction.Service.ServiceImpl.UploadFile;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("postAsset")
public class AssetController {
    private final UserService userService;
    private final LandService landService;
    private final AssetRegistrationService assetRegistrationService;

    public AssetController(UserService userService, LandService landService, AssetRegistrationService assetRegistrationService) {
        this.userService = userService;
        this.landService = landService;
        this.assetRegistrationService = assetRegistrationService;
    }

    @GetMapping("form")
    public String formAsset(Model model) {
       LandDTO landDTO = new LandDTO();
        model.addAttribute("land", landDTO);
        return "customer/land-registratrion";
    }

    @PostMapping("saveForm")
    public String saveAsset(@ModelAttribute("assetFrom") LandDTO landDTO, Principal principal) {
        MultipartFile document  = landDTO.getDocument();
        List<MultipartFile> images = landDTO.getImages();
        User user = userService.findByEmail(principal.getName());
                Land land = new Land(user.getPhoneNumber(),
                landDTO.getPrice(),
                landDTO.getDescription() , landDTO.getLocation(),
                user, landDTO.getLandName(), landDTO.getWard(),
                landDTO.getDistrict(), landDTO.getProvince(),landDTO.getSquare());
        UploadFile uploadFile = new UploadFile();
        uploadFile.upLoadDocumentAsset(document, land);
        AssetRegistration assetRegistration = new AssetRegistration();
        assetRegistration.setUser(user);
        assetRegistration.setLand(land);
        uploadFile.UploadImagesForLand(images, land);
        LocalDateTime createdDate = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        assetRegistration.setRegistrationDate(createdDate);
        landService.save(land);
        assetRegistrationService.save(assetRegistration);
        return "/customer/single-list";
    }
}
