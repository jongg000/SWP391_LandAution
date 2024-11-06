package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.DTO.AdminCreateDTO;
import com.se1858.G5.LandAuction.Entity.Status;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Entity.Violation;
import com.se1858.G5.LandAuction.Repository.RolesRepository;
import com.se1858.G5.LandAuction.Repository.StatusRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Service.*;
import com.se1858.G5.LandAuction.util.UploadFile;
import com.se1858.G5.LandAuction.Service.*;
import com.se1858.G5.LandAuction.util.UploadFile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {

    UserService userService;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RolesRepository roleRepository;
    PaymentService paymentService;
    AuctionService auctionService;
    RoleService roleService;
    StatusRepository statusRepository;
    ViolationService violationService;
    EmailService emailService;


    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository, RolesRepository roleRepository, PaymentService paymentService, AuctionService auctionService, RoleService roleService, StatusRepository statusRepository, ViolationService violationService, EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.paymentService = paymentService;
        this.auctionService = auctionService;
        this.roleService = roleService;
        this.statusRepository = statusRepository;
        this.violationService = violationService;
        this.emailService = emailService;
    }

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
    public String showAllUser(@RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "userId", required = false) String userId,
                              Model model) {
        int pageSize = 6; // Display 10 users per page
        Page<User> usersPage;
        if (userId != null && !userId.isEmpty()) {
            try {
                int parsedUserId = Integer.parseInt(userId); // Convert userId to Integer
                // Search by userId as integer
                usersPage = userService.findUsersById(parsedUserId, PageRequest.of(page, pageSize));
            } catch (NumberFormatException e) {
                // Handle the case where userId is not a valid integer
                model.addAttribute("error", "User ID must be a number");
                usersPage = userService.findUsersByRoleExcluding(PageRequest.of(page, pageSize), 2);// Default to all users if invalid input
            }
        } else {
            // Get all users with pagination
            usersPage = userService.findUsersByRoleExcluding(PageRequest.of(page, pageSize), 2);
        }

        model.addAttribute("users", usersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("userId", userId);
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
        model.addAttribute("createDTO", new AdminCreateDTO());
        return "admin/create-account";
    }



    @PostMapping("/create-account")
    public String createAccount(@ModelAttribute("createDTO") AdminCreateDTO adminCreateDTO,
                                @RequestParam("role") int role,
                                BindingResult bindingResult,
                                Model model) {


        // Kiểm tra email và số điện thoại tồn tại trước khi tạo người dùng mới
        if (userService.existsByEmail(adminCreateDTO.getEmail())) {
            model.addAttribute("emailError", "Email này đã tồn tại.");
        }

        if (userService.existsByPhoneNumber(adminCreateDTO.getPhoneNumber())) {
            model.addAttribute("phoneError", "Số điện thoại đã tồn tại.");
        }
        if (userService.existsByNationalID(adminCreateDTO.getNationalID())) {
            model.addAttribute("nationError","Căn cước công dân đã tồn tại.");
        }

        // Kiểm tra nếu có lỗi trong BindingResult (các thông báo lỗi từ validate)
        if (bindingResult.hasErrors() || model.containsAttribute("emailError") || model.containsAttribute("phoneError")|| model.containsAttribute("nationError")) {
            return "admin/create-account"; // Trả về trang đăng ký nếu có lỗi
        }

        User user = new User();
        UploadFile uploadFile = new UploadFile();
        user.setEmail(adminCreateDTO.getEmail());
        user.setPhoneNumber(adminCreateDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(adminCreateDTO.getPassword()));
        user.setFirstName(adminCreateDTO.getFirstName());
        user.setLastName(adminCreateDTO.getLastName());
        user.setDob(adminCreateDTO.getDob());
        user.setGender(adminCreateDTO.getGender());
        user.setNationalID(adminCreateDTO.getNationalID());
        user.setAddress(adminCreateDTO.getAddress());

        if (adminCreateDTO.getRole() == null) {
            model.addAttribute("roleError", "Hãy chọn vai trò.");
            return "admin/create-account";
        }

        if (role == 3){
            user.setRole(roleService.findByRoleID(3));
        }
        if (role == 4){
            user.setRole(roleService.findByRoleID(4));
        }

        uploadFile.upLoadDocumentAvata(adminCreateDTO.getAvatar(), user);
        uploadFile.UploadImagesNationalF(adminCreateDTO.getNationalFrontImage(), user);
        uploadFile.UploadImagesNationalB(adminCreateDTO.getNationalBackImage(), user);


        Status status = new Status();
        status.setStatusID(2); // Ví dụ: 1 là trạng thái "Active" hoặc tương đương
        user.setStatus(status);

        userService.save(user);
        return "redirect:/management";
    }

    @GetMapping("/adminChangePassword")
    public String showChangePasswordForm(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Đưa thông tin người dùng vào model (nếu cần)
        model.addAttribute("user", user);

        return "admin/change-password";
    }


    @PostMapping("/adminChangePassword")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Kiểm tra mật khẩu hiện tại có đúng không
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng.");
            model.addAttribute("user", user);
            return "admin/change-password";
        }

        // Kiểm tra nếu mật khẩu mới giống với mật khẩu hiện tại
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu mới không được giống mật khẩu hiện tại.");
            model.addAttribute("user", user);
            return "admin/change-password";
        }

        // Kiểm tra xem mật khẩu mới có khớp với xác nhận mật khẩu không
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
            model.addAttribute("user", user);
            return "admin/change-password";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);

        model.addAttribute("success", "Mật khẩu đã được cập nhập.");
        model.addAttribute("user", user);
        return  "admin/change-password";
    }

    @GetMapping("/violation")
    public String showViolationForm(
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        int pageSize = 6; // Set the page size as needed (e.g., 10 items per page)
        Page<Violation> violationsPage = violationService.getAllViolations(PageRequest.of(page, pageSize));

        model.addAttribute("violations", violationsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", violationsPage.getTotalPages());
        return "admin/violation";
    }


    @GetMapping("/request-account")
    public String showRequestAccountlist(Model model, Principal principal) {
        List<User> allUsers = userService.findUsersByStatusAndRole(statusRepository.getById(1), roleService.findByRoleID(1));
        List<Status> allStatuses = statusRepository.findAll();
        model.addAttribute("statuses", allStatuses);
        model.addAttribute("users", allUsers);
        return "admin/request-listing";
    }
    @GetMapping("/request-detail/{id}")
    public String showRequestDetail(@PathVariable int id, Model model, Principal principal) {
        User user = userService.findByUserId(id);
        model.addAttribute("user", user);
        return "admin/request-detail";
    }
    @GetMapping("/handle-request")
    public String handleRequest(@RequestParam("decision") String decision,
                                @RequestParam("userid") int id,
                                @RequestParam("comments") String comment) {
        User user = userRepository.findById(id).get();
        System.out.println(user.getLastName());
        System.out.println(decision);
        if(decision.equals("approve")){
            user.setStatus(statusRepository.getById(2));
            userService.save(user);
        }else{
            emailService.sendSimpleMail(user.getEmail(), "Kết quả xác nhận hồ sơ người dùng", comment);
        }
        return "redirect:request-account";
    }

}
