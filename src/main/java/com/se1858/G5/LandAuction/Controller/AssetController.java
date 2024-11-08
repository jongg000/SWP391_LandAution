
package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Service.*;
import com.se1858.G5.LandAuction.util.UploadFile;
import com.se1858.G5.LandAuction.Service.StatusService;
import com.se1858.G5.LandAuction.Service.UserService;
import lombok.RequiredArgsConstructor;
import com.se1858.G5.LandAuction.util.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/asset")
public class AssetController {

    private  UserService userService;
    private  LandService landService;
    private  AssetService assetService;
    private  AssetRegistrationService assetRegistrationService;
    private  LandRepository landRepository;
    private  StatusService statusService;

    @Autowired
    public AssetController(UserService userService, LandService landService, AssetService assetService, AssetRegistrationService assetRegistrationService, LandRepository landRepository, StatusService statusService) {
        this.userService = userService;
        this.landService = landService;
        this.assetService = assetService;
        this.assetRegistrationService = assetRegistrationService;
        this.landRepository = landRepository;
        this.statusService = statusService;
    }

    @GetMapping("/post-asset")
    public String formAsset(Model model) {
       LandDTO landDTO = new LandDTO();
        model.addAttribute("land", landDTO);
        return "customer/land-registratrion";
    }


    @PostMapping("saveForm")
    public String saveAsset(@ModelAttribute("assetFrom") LandDTO landDTO, Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        Land land = new Land(landDTO.getLength(),landDTO.getWidth(), landDTO.getSquare(),
                                     landDTO.getContact(),landDTO.getPrice(), landDTO.getDescription(),
                                     landDTO.getLocation(), user, landDTO.getName(),landDTO.getWard(),
                                     landDTO.getDistrict(), landDTO.getProvince());
        UploadFile uploadFile = new UploadFile();
        land.setContact(user.getPhoneNumber());
        uploadFile.upLoadDocumentAsset(landDTO.getDocument(), land);
        AssetRegistration assetRegistration = new AssetRegistration();
        uploadFile.UploadImagesForLand(landDTO.getImages(), land);
        LocalDateTime createdDate = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        landDTO.setCreatedDate(createdDate);
        assetRegistration.setLand(land);
        assetRegistration.setRegistrationDate(createdDate);
        land.setAssetRegistration(assetRegistration);
        assetRegistration.setStatus(statusService.getStatusById(10));
        landService.save(land);
        System.out.println(land.toString());
        assetRegistrationService.save(assetRegistration);
        return "redirect:/asset";
    }


    @GetMapping()
    public String list(@RequestParam(value = "status", required = false) String status,
                        Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        List<Land> list = null;
        if(status == null || status.equals("0")) {
            list = landRepository.findLandsByUserIdOrderedByRegistrationDate(user.getUserId());
        }if(status != null){
            list = landRepository.findLandsByUserIdAndStatusId(user.getUserId(), Integer.parseInt(status));
        }
        long count = landService.countByUser(user);
        model.addAttribute("list", list);
        model.addAttribute("count", count);
        model.addAttribute("status", user.getStatus().getStatusID());
        return "customer/asset-list";
    }
    @GetMapping("/asset-detail/{id}")
    public String detail(Principal principal, @PathVariable int id, Model model) {
        AssetRegistration assetRegistration = assetRegistrationService.getAssetRegistrationByID(id);
        System.out.println(assetRegistration.getLand().getName());
        model.addAttribute("assetRegistration", assetRegistration);
        return "customer/asset-detail";
    }

}
