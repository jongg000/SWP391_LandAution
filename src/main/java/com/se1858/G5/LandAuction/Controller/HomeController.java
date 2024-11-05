package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.UserRegisterDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.RolesRepository;
import com.se1858.G5.LandAuction.Repository.StatusRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Service.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Getter
@Controller
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {

    AssetService assetService;
    LandService landService;
    UserService userService;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RolesRepository roleRepository;
    StatusRepository statusRepository;
    NewsService newsService;
    AuctionService auctionService;

    @Autowired
    public HomeController(AssetService assetService, LandService landService, UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository, RolesRepository roleRepository, StatusRepository statusRepository, NewsService newsService, AuctionService auctionService) {
        this.assetService = assetService;
        this.landService = landService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.newsService = newsService;
        this.auctionService = auctionService;
    }

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // Chuyển hướng đến trang home
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<News> newsList = newsService.findTop4ByOrderByNewsIdDesc();
        List<Land> allLands = assetService.findTop4ByOrderByLandIdDesc();

        // Assuming `newsList` has images and we're picking the first image as "latestImage"

        model.addAttribute("allLands", allLands);
        model.addAttribute("newsList", newsList);
        return "home"; // Trả về tên của file HTML home.html
    }
    @GetMapping("/aboutUs")
    public String showAboutUs() {

        return "aboutUs"; // Trả về tên của file HTML home.html
    }
    @GetMapping("/search")
    public String searchLandByKey(@RequestParam("keyword") String keyword, Model model) {

        List<Land> allLands = assetService.findAllByName(keyword);
        // Assuming `newsList` has images and we're picking the first image as "latestImage"

        model.addAttribute("allLands", allLands);
        return "land"; // Name of the view for displaying search results
    }
    @GetMapping("/someProtectedPage")
    public String someProtectedPage(HttpServletRequest request) {
        // Kiểm tra nếu người dùng chưa đăng nhập
        if (request.getUserPrincipal() == null) {
            // Lưu URL hiện tại vào session
            request.getSession().setAttribute("redirectUrl", request.getRequestURI());
            return "redirect:/login";
        }
        return "403";
    }


    @GetMapping("/login")
    public String login(Principal principal, HttpServletRequest session) {
        if (principal != null) {
            // Lấy URL từ session và chuyển hướng nếu có, ngược lại thì về home
            String redirectUrl = (String) session.getAttribute("redirectUrl");
            session.removeAttribute("redirectUrl"); // Xóa URL khỏi session sau khi lấy
            return "redirect:" + (redirectUrl != null ? redirectUrl : "/home");
        }
        return "login";
    }


}
