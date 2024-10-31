package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Service.AssetService;
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

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // Chuyển hướng đến trang home
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {

        List<Land> allLands = assetService.findAll();
      //  List<Land> landsByName = assetService.findAllByName("example"); // Adjust the keyword as needed

        // Add data to the model
        model.addAttribute("allLands", allLands);
//        model.addAttribute("landsByName", landsByName);


        return "home"; // Trả về tên của file HTML home.html
    }

}
