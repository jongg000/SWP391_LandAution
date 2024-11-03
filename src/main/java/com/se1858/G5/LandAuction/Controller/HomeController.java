package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Service.AssetService;
import com.se1858.G5.LandAuction.Service.LandService;
import com.se1858.G5.LandAuction.Service.NewsService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Getter
@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {

    private final AssetService assetService;
    private final LandService landService;
    private final NewsService newsService;

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // Chuyển hướng đến trang home
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<News> newsList = newsService.findTop4ByOrderByTimeDesc();
        List<Land> allLands = assetService.findTop4ByOrderByLandIdDesc();
        model.addAttribute("allLands", allLands);
        model.addAttribute("newsList", newsList);
        return "home"; // Trả về tên của file HTML home.html
    }
    @GetMapping("/aboutUs")
    public String showAboutUs() {

        return "aboutUs"; // Trả về tên của file HTML home.html
    }

}
