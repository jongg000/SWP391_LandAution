
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

import javax.servlet.http.HttpSession;
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
        model.addAttribute("assetFrom", landDTO);
        return "customer/land-registratrion";
    }

    @PostMapping("saveForm")
    public String saveAsset(@ModelAttribute("assetFrom") LandDTO landDTO, Model model, HttpSession session) {
        MultipartFile document  = landDTO.getDocument();
        List<MultipartFile> images = landDTO.getImages();
        User user = (User) session.getAttribute("user");
        String contact = landDTO.getContact();
        for(int i = 0; i < contact.length(); i++){
            if (!Character.isDigit(contact.charAt(i))){

            }
        }
        Land land = new Land(landDTO.getContact(),
                landDTO.getPrice(),
                landDTO.getDescription() , landDTO.getLocation(),
                user, landDTO.getLandName(), landDTO.getWard(),
                landDTO.getDistrict(), landDTO.getProvince());
        UploadFile uploadFile = new UploadFile();
        uploadFile.upLoadDocumentAsset(document, land);
        AssetRegistration assetRegistration = new AssetRegistration(user);
        assetRegistration.setLand(land);
        uploadFile.UploadImagesForLand(images, land);
        landService.save(land);
        assetRegistrationService.save(assetRegistration);
        return "/customer/single-list";
    }
}
