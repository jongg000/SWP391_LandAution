package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.Service.LandService;
import com.se1858.G5.LandAuction.Service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/land")
public class LandController {

    @Autowired
    private LandService landService;

    @RequestMapping("/list")
    public String listNews(ModelMap model) {
        model.addAttribute("LIST_LAND", landService.findAllLandDetailsWithAuctions());
        return "land/home";
    }
}
