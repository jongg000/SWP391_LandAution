package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.DTO.ProfileDTO;
import com.se1858.G5.LandAuction.DTO.UserRegisterDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.*;
import com.se1858.G5.LandAuction.Service.ServiceImpl.UploadFile;
import com.se1858.G5.LandAuction.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RolesRepository roleRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";
    private final StatusRepository statusRepository;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO", new UserRegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerDTO") UserRegisterDTO userRegisterDTO,BindingResult bindingResult,  Model model) {
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

    @GetMapping("/login")
    public String login() {
        return "login"; // tên file login.html trong thư mục templates
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // Lấy thông tin người dùng hiện tại từ tên đăng nhập (email)
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Đưa thông tin người dùng vào model
        model.addAttribute("user", user);

        // Trả về trang profile
        return "customer/profile";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordForm(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Đưa thông tin người dùng vào model (nếu cần)
        model.addAttribute("user", user);

        return "customer/change-password";
    }


    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Kiểm tra mật khẩu hiện tại có đúng không
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng.");
            return "customer/change-password";
        }

        // Kiểm tra xem mật khẩu mới có khớp với xác nhận mật khẩu không
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
            return "customer/change-password";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);

        model.addAttribute("success", "Password changed successfully.");
        return "customer/change-password";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        model.addAttribute("user", user);
        return "customer/edit-profile"; // Trả về trang cập nhật thông tin
    }


    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute ProfileDTO userProfileDTO,
                                Principal principal, Model model) throws IOException {

        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Cập nhật thông tin cá nhân
        user.setFirstName(userProfileDTO.getFirstName());
        user.setLastName(userProfileDTO.getLastName());
        user.setPhoneNumber(userProfileDTO.getPhoneNumber());
        user.setAddress(userProfileDTO.getAddress());
        user.setDob(userProfileDTO.getDob());

        UploadFile uploadFile = new UploadFile();

        // Lưu avatar nếu có
        if (userProfileDTO.getAvatar() != null && !userProfileDTO.getAvatar().isEmpty()) {
            uploadFile.upLoadDocumentAvata(userProfileDTO.getAvatar(),user);
        }

        // Lưu hình ảnh CMND nếu có
        if (userProfileDTO.getNationalFrontImage() != null && !userProfileDTO.getNationalFrontImage().isEmpty()) {
            String frontImageFileName = saveFile(userProfileDTO.getNationalFrontImage());
            user.setNationalFrontImage(frontImageFileName);
        }

        if (userProfileDTO.getNationalBackImage() != null && !userProfileDTO.getNationalBackImage().isEmpty()) {
            String backImageFileName = saveFile(userProfileDTO.getNationalBackImage());
            user.setNationalBackImage(backImageFileName);
        }

        // Lưu thông tin đã cập nhật vào database
        userService.save(user);
        model.addAttribute("success", "Cập nhật thông tin cá nhân thành công.");
        return "redirect:/customer/profile";
    }
    private String saveFile(MultipartFile file) throws IOException {
        // Tạo tên file ngẫu nhiên để tránh trùng lặp
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return "/images/" + fileName; // Đường dẫn trả về cho frontend
    }

}