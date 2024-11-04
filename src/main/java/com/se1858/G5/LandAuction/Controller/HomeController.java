package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.UserRegisterDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.RolesRepository;
import com.se1858.G5.LandAuction.Repository.StatusRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Service.AssetService;
import com.se1858.G5.LandAuction.Service.LandService;
import com.se1858.G5.LandAuction.Service.NewsService;
import com.se1858.G5.LandAuction.Service.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {

    private final AssetService assetService;
    private final LandService landService;
    UserService userService;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RolesRepository roleRepository;
    private final StatusRepository statusRepository;
    private final NewsService newsService;

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // Chuyển hướng đến trang home
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<News> newsList = newsService.findTop4ByOrderByTimeDesc();
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



    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO", new UserRegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerDTO") UserRegisterDTO userRegisterDTO, BindingResult bindingResult, Model model) {
        // Kiểm tra email và số điện thoại tồn tại trước khi tạo người dùng mới
        if (userService.existsByEmail(userRegisterDTO.getEmail())) {
            model.addAttribute("emailError", "Email này đã tồn tại.");
        }

        if (userService.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())) {
            model.addAttribute("phoneError", "Số điện thoại đã tồn tại.");
        }

        // Kiểm tra nếu có lỗi trong BindingResult (các thông báo lỗi từ validate)
        if (bindingResult.hasErrors() || model.containsAttribute("emailError") || model.containsAttribute("phoneError")) {
            return "register"; // Trả về trang đăng ký nếu có lỗi
        }
        // Gọi phương thức createUser để xử lý việc tạo người dùng
        createUser(userRegisterDTO, 1);

        // Nếu đăng ký thành công, chuyển hướng đến trang login
        model.addAttribute("success", "Đăng ký thành công!");
        return "redirect:/login";
    }

    private void createUser(UserRegisterDTO userRegisterDTO, int roleId) {
        // Tạo người dùng mới và gán các thuộc tính
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setEmail(userRegisterDTO.getEmail());
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setPhoneNumber(userRegisterDTO.getPhoneNumber());

        // Gán vai trò (role) cho người dùng
        Roles role = roleRepository.findById(roleId).orElseThrow(() -> new IllegalArgumentException("Invalid role ID"));
        user.setRole(role);

        // Gán trạng thái mặc định cho người dùng
        Status status = new Status();
        status.setStatusID(1); // Ví dụ: 1 là trạng thái "Active" hoặc tương đương
        user.setStatus(status);

        // Lưu người dùng vào cơ sở dữ liệu
        userService.save(user);
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


    @GetMapping("/land")
    public String land(Model model) {
        List<Land> allLands = assetService.findAll();
        // Assuming `newsList` has images and we're picking the first image as "latestImage"
        model.addAttribute("allLands", allLands);
        return "land"; // Trả về tên của file HTML home.html
    }


}
