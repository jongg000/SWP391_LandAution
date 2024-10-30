package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.AssetInfoDTO;
import com.se1858.G5.LandAuction.Repository.LandRepository;
import com.se1858.G5.LandAuction.Service.AssetRegistrationService;
import com.se1858.G5.LandAuction.Service.NewsService;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import java.util.List;


@Controller

public class HistoryController {
    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    LandRepository landRepository;

    @Autowired
    private AssetRegistrationService assetRegistrationService;

    @RequestMapping("/history/{id}")
    public String detail(ModelMap model,
                         @PathVariable(value = "id", required = true) int id) {

        List<AssetInfoDTO>  assetInfoDTOS = assetRegistrationService.findAssetInfoByDocumentId(id);
        model.addAttribute("assetInfoDTOS", assetInfoDTOS);
        return "customer/historyRegister";
    }


}