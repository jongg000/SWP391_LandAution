
package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.AssetRegistrationDTO;
import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Repository.AssetRegistrationRepository;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Service.UploadFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("postAsset")
public class AssetController {
    private LandRepository landRepository;
    private AssetRegistrationRepository assetRegistrationRepository;

    @GetMapping("form")
    public String formAsset(Model model) {
        AssetRegistrationDTO assetRegistrationDTO = new AssetRegistrationDTO();
        model.addAttribute("assetFrom", assetRegistrationDTO);
        return "Customer/Land_registration";
    }
    @PostMapping("saveAssetRegistration")
    public String saveAsset(@ModelAttribute("assetFrom") AssetRegistrationDTO assetRegistrationDTO) {
        String Province = assetRegistrationDTO.getProvince() ;
        String District = assetRegistrationDTO.getDistrict() ;
        String Ward = assetRegistrationDTO.getWard() ;
        String Location  = assetRegistrationDTO.getLocation() ;
        String Description = assetRegistrationDTO.getDescription() ;
        Double price  = assetRegistrationDTO.getPrice() ;
        String contact = assetRegistrationDTO.getContact() ;
        MultipartFile document  = assetRegistrationDTO.getDocument() ;
        List<MultipartFile> images =  assetRegistrationDTO.getImages();
        Land land = new Land();
        AssetRegistration assetRegistration = new AssetRegistration();
        UploadFile uploadFile = new UploadFile();
        uploadFile.uploadImageForLand(images, land);
        uploadFile.upLoadDocumentAsset(document ,assetRegistration);
        landRepository.save(land);
        assetRegistration.setLand(land);
        assetRegistrationRepository.save(assetRegistration);
        return "/Customer/land-registratrion";
    }
}
