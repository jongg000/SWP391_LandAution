
package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.FullAssetInfoDTO;
import com.se1858.G5.LandAuction.DTO.LandDTO;
import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.Status;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import com.se1858.G5.LandAuction.Service.LandService;
import com.se1858.G5.LandAuction.Service.ServiceImpl.UploadFile;
import com.se1858.G5.LandAuction.Service.StatusService;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("postAsset")
public class AssetController {
    private final UserService userService;
    private final LandService landService;
    private final AssetRegistrationService assetRegistrationService;
    private final StatusService statusService;

    public AssetController(UserService userService, LandService landService, AssetRegistrationService assetRegistrationService, StatusService statusService) {
        this.userService = userService;
        this.landService = landService;
        this.assetRegistrationService = assetRegistrationService;
        this.statusService = statusService;
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
                user, landDTO.getName(), landDTO.getWard(),
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

    @RequestMapping("/asset")
    public String detail(ModelMap model, @RequestParam(value = "action", required = false) String action,
                         @RequestParam(value = "id", required = true) int id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        User userLogin = userService.findByEmail(username);
        FullAssetInfoDTO  asset = assetRegistrationService.findFullAssetInfoByDocumentId(id, userLogin.getUserId());
        if(action != null && action.equals("cancel")) {
            AssetRegistration a = assetRegistrationService.findAssetRegistrationByDocumentId(id);
            Status status = new Status();
            status.setStatusID(9);
            a.setStatus(status);
            assetRegistrationService.save(a);
        }
        model.addAttribute("asset", asset);
        model.addAttribute("assetImg", asset.getImages());
        return "customer/historyRegister";
    }

    @RequestMapping("/asset/list")
    public String list(ModelMap model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "status", required = false) Integer status
            , HttpSession session) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);
        String username = (String) session.getAttribute("username");
        User userLogin = userService.findByEmail(username);
        Page<FullAssetInfoDTO> list = assetRegistrationService.findFullAssetInfo(pageable, userLogin.getUserId(), status);//, null
        List<Status> statusList = statusService.getAllStatus();
        model.addAttribute("LIST_ASSET", list);
        model.addAttribute("LIST_STATUS", statusList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", list.getTotalPages());
        return "customer/listAsset";
    }
}
