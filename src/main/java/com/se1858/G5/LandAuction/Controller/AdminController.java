package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.DTO.UserRegisterDTO;
import com.se1858.G5.LandAuction.Entity.Roles;
import com.se1858.G5.LandAuction.Entity.Status;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.RolesRepository;
import com.se1858.G5.LandAuction.Repository.StatusRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Service.AuctionService;
import com.se1858.G5.LandAuction.Service.PaymentService;
import com.se1858.G5.LandAuction.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {

    UserService userService;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RolesRepository roleRepository;
    PaymentService paymentService;
    AuctionService auctionService;

    private final StatusRepository statusRepository;


    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        String email = principal.getName();
        User user = userService.findByEmail(email);
        // Lấy tổng số tiền thanh toán
        long totalPaymentAmount = paymentService.getTotalPaymentAmount();
        // Lấy tổng số người dùng
        long totalUsers = userService.getTotalUsers();
        // Lấy tổng số người dùng
        long totalAuctions = auctionService.getTotalAuctions();
        List<User> users = userService.findTop3UsersByOrderByIdDesc();
        Map<Month, Long> monthlyPaymentAmounts = paymentService.getMonthlyPaymentAmounts();

        model.addAttribute("users", users);
        model.addAttribute("user", user);
        model.addAttribute("totalAuctions", totalAuctions);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalPaymentAmount", totalPaymentAmount);
        model.addAttribute("monthlyPaymentAmounts", monthlyPaymentAmounts);
        // Nếu bạn có bảng khác để lấy thêm thông tin, hãy thêm vào đâ
        return "admin/dashboard";
    }

    @GetMapping("/management")
    public String showAllUser(Model model) {
        List<User> allUsers = userRepository.findAll();
        List<User> filteredUsers = allUsers.stream()
                .filter(user -> user.getRole().getRoleID() != 2)
                .collect(Collectors.toList());
        model.addAttribute("users", filteredUsers);
        return "admin/manage-account";
    }

    @PostMapping("/banUser/{id}")
    public String banUser(@PathVariable("id") int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            Status banStatus = statusRepository.findById(3).orElse(null);
            if (banStatus != null) {
                user.setStatus(banStatus);
                userRepository.save(user);
            }
        }
        return "redirect:/management";
    }

    @GetMapping("/create-account")
    public String showCreateAccount(Model model) {
        List<Roles> roles = roleRepository.findAll();

        model.addAttribute("roles", roles);
        model.addAttribute("registerDTO", new UserRegisterDTO());

        return "admin/create-account";
    }


    @PostMapping("/admin/create-account")
    public String createAccount(UserRegisterDTO userRegisterDTO, Model model) {
//        return createUser(userRegisterDTO, model, 0);
        return null;
    }

}
