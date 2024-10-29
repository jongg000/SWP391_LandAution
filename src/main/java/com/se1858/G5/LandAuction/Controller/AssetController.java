
package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.LandRepository;
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
    private final LandRepository landRepository;

    public AssetController(UserService userService, LandService landService, AssetRegistrationService assetRegistrationService, LandRepository landRepository) {
        this.userService = userService;
        this.landService = landService;
        this.assetRegistrationService = assetRegistrationService;
        this.landRepository = landRepository;
    }

    @GetMapping("form")
    public String formAsset(Model model) {
       LandDTO landDTO = new LandDTO();
        model.addAttribute("land", landDTO);
        return "customer/land-registratrion";
    }

    @GetMapping("/image")
    public String testImage(Model model) {
        Land land = landRepository.getById(36);
        String url = land.getPath();
        model.addAttribute("land", url);
        return "customer/Success";
    }

    @PostMapping("saveForm")
    public String saveAsset(@ModelAttribute("assetFrom") LandDTO landDTO, Principal principal, Model model) {

        User user = userService.findByEmail(principal.getName());
                Land land = new Land(landDTO.getLength(),landDTO.getWidth(), landDTO.getSquare(),
                                    landDTO.getContact(),landDTO.getPrice(), landDTO.getDescription(), landDTO.getLocation(),
                                     user, landDTO.getLandName(),landDTO.getWard(), landDTO.getDistrict(), landDTO.getProvince());
        UploadFile uploadFile = new UploadFile();
        uploadFile.upLoadDocumentAsset(landDTO.getDocument(), land);
        AssetRegistration assetRegistration = new AssetRegistration();
        assetRegistration.setLand(land);
        uploadFile.UploadImagesForLand(landDTO.getImages(), land);
        LocalDateTime createdDate = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        assetRegistration.setRegistrationDate(createdDate);
        landService.save(land);
        assetRegistrationService.save(assetRegistration);
        model.addAttribute("land", land);
        return "/customer/Success";
    }
}
