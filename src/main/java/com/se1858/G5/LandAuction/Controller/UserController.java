package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.DTO.UserRegisterDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.*;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
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
    private static final String uploadDir = "C:/Users/ngoda/Downloads/LandAuction/LandAuction/src/main/resources/static/images/";
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
                model.addAttribute("emailError", "This email address is already in use.");
        }

        if (userService.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())) {
            model.addAttribute("phoneError", "This phone number is already in use.");
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
        user.setWallet(0.0f);  // Khởi tạo số dư ban đầu

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
        return "profile";
    }

    @GetMapping("/changePassword")
    public String showChangePasswordForm(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Đưa thông tin người dùng vào model (nếu cần)
        model.addAttribute("user", user);

        return "changePassword";
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
            return "changePassword";
        }

        // Kiểm tra xem mật khẩu mới có khớp với xác nhận mật khẩu không
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
            return "changePassword";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);

        model.addAttribute("success", "Password changed successfully.");
        return "changePassword";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        model.addAttribute("user", user);
        return "editProfile"; // Trả về trang cập nhật thông tin
    }


    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("user") User userForm, @RequestParam("avatar") MultipartFile avatar,
                                @RequestParam("nationalFrontImage") MultipartFile nationalFrontImage,
                                @RequestParam("nationalBackImage") MultipartFile nationalBackImage,
                                Principal principal, Model model) throws IOException {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Cập nhật thông tin cá nhân
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAddress(userForm.getAddress());
        user.setDob(userForm.getDob());
        if (userService.existsByNationalID(userForm.getNationalID())) {
            model.addAttribute("nationalID", "Hiện tại đã tồn tại CMND này");
        }
        user.setNationalID(userForm.getNationalID());

        // Lưu avatar nếu có
        if (!avatar.isEmpty()) {
            String avatarFileName = saveFile(avatar);
            user.setAvatar(avatarFileName);
        }

        // Lưu hình ảnh CMND nếu có
        if (!nationalFrontImage.isEmpty()) {
            String frontImageFileName = saveFile(nationalFrontImage);
            user.setNationalFrontImage(frontImageFileName);
        }

        if (!nationalBackImage.isEmpty()) {
            String backImageFileName = saveFile(nationalBackImage);
            user.setNationalBackImage(backImageFileName);
        }

        // Lưu thông tin đã cập nhật
        userService.save(user);

        model.addAttribute("success", "Cập nhập thong tin cá nhân thành công.");
        return "redirect:/profile"; // Trả về trang hồ sơ sau khi cập nhật
    }

    @GetMapping("/getAllUser")
    public String showAllUser(Model model) {
        List<User> allUsers = userRepository.findAll();
        List<User> filteredUsers = allUsers.stream()
                .filter(user -> user.getRole().getRoleID() != 2)
                .collect(Collectors.toList());
        model.addAttribute("users", filteredUsers);
        return "admin/manageAccount";
    }

    @PostMapping("/admin/banUser/{id}")
    public String banUser(@PathVariable("id") int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            Status banStatus = statusRepository.findById(3).orElse(null);
            if (banStatus != null) {
                user.setStatus(banStatus);
                userRepository.save(user);
            }
        }
        return "redirect:/manageAccount";
    }

    @GetMapping("/admin/create-account")
    public String showCreateAccount(Model model) {
        List<Roles> roles = roleRepository.findAll();

        model.addAttribute("roles", roles);
        model.addAttribute("registerDTO", new UserRegisterDTO());

        return "create-account";
    }


    @PostMapping("/admin/create-account")
    public String createAccount(UserRegisterDTO userRegisterDTO, Model model) {
//        return createUser(userRegisterDTO, model, 0);
        return null;
    }

    @GetMapping("/admin/updateUser/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            model.addAttribute("error", "User not found.");
            return "redirect:/getAllUser";
        }
        List<Roles> roles = roleRepository.findAll();

        model.addAttribute("roles", roles);
        model.addAttribute("existingUser", existingUser);
        return "update-user";
    }

    @PostMapping("/admin/updateUser/{id}")
    public String updateUser(@PathVariable("id") int id,
                             @ModelAttribute User updatedUser,
                             @RequestParam("avatarFile") MultipartFile avatarFile,
                             Model model) {
        User existingUser = userRepository.findById(Math.toIntExact(id)).orElse(null);
        if (existingUser == null) {
            model.addAttribute("error", "User not found.");
            return "redirect:/getAllUser";
        }

        if (isUsernameOrEmailTaken(updatedUser, model, id)) {
            model.addAttribute("existingUser", existingUser);
            model.addAttribute("roles", roleRepository.findAll());
            return "update-user";
        }
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setDob(updatedUser.getDob());
        existingUser.setNationalID(updatedUser.getNationalID());


        if (updatedUser.getRole() != null && updatedUser.getRole().getRoleID() != 0) {
            Roles role = roleRepository.findById(Math.toIntExact(updatedUser.getRole().getRoleID())).orElse(null);
            if (role != null) {
                existingUser.setRole(role);
            }
        }

        if (updatedUser.getStatus() != null && updatedUser.getStatus().getStatusID() != 0) {
            Status status = statusRepository.findById(updatedUser.getStatus().getStatusID()).orElse(null);
            if (status != null) {
                existingUser.setStatus(status);
            }
        }

        if (!avatarFile.isEmpty()) {
            existingUser.setAvatar(saveFile(avatarFile));
        }
        userRepository.save(existingUser);
        return "redirect:/getAllUser";
    }

    private boolean isUsernameOrEmailTaken(User updatedUser, Model model, int userId) {

        User userWithSameEmail = userRepository.findByEmail(updatedUser.getEmail());
        if (userWithSameEmail != null && !Objects.equals(userWithSameEmail.getUserId(), userId)) {
            model.addAttribute("error", "Email is already taken by another user.");
            return true;
        }
        return false;
    }

    private String saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            // Lưu file vào thư mục static/images/

            Path path = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }


}