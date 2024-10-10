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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private static final String UPLOAD_DIR = "C:\\Users\\ngoda\\Downloads\\LandAuction\\LandAuction\\src\\main\\resources\\static\\img";
    private final StatusRepository statusRepository;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO", new UserRegisterDTO());
        return "register";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        return "customer/home"; // Trả về tên của file HTML home.html
    }

    @PostMapping("/register")
    public String register(UserRegisterDTO userRegisterDTO, Model model) {
        // Gọi phương thức createUser để xử lý việc tạo người dùng
        String resultPage = createUser(userRegisterDTO, model, 1, "register");

        // Nếu đăng ký thành công, chuyển hướng đến trang login
        if (!model.containsAttribute("error")) {
            return "redirect:/login"; // Nếu không có lỗi, chuyển đến trang đăng nhập
        }

        // Nếu có lỗi, trả về trang hiện tại (trang đăng ký)
        return resultPage;
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
        return "redirect:/getAllUser";
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
        return createUser(userRegisterDTO, model, 0, "create-account");
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

        existingUser.setUserName(updatedUser.getUserName());
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
            try {
                existingUser.setAvatar(uploadAvatarFile(avatarFile));
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Avatar upload failed.");
                model.addAttribute("existingUser", existingUser);
                model.addAttribute("roles", roleRepository.findAll());
                return "update-user";
            }
        }
        userRepository.save(existingUser);
        return "redirect:/getAllUser";
    }

    @GetMapping("/admin/updatePassword/{id}")
    public String showUpdatePasswordForm(@PathVariable("id") int id, Model model) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            model.addAttribute("error", "User not found.");
            return "redirect:/admin/getAllUser";
        }
        model.addAttribute("existingUser", existingUser);
        return "update-password";
    }

    @PostMapping("/admin/updatePassword/{id}")
    public String updatePassword(@PathVariable("id") int id,
                                 @RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {

        User existingUser = userRepository.findById(Math.toIntExact(id)).orElse(null);
        if (existingUser == null) {
            model.addAttribute("error", "User not found.");
            return "redirect:/getAllUser";
        }

        if (!passwordEncoder.matches(currentPassword, existingUser.getPassword())) {
            model.addAttribute("error", "Current password is incorrect.");
            model.addAttribute("existingUser", existingUser);
            return "update-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "New passwords do not match.");
            model.addAttribute("existingUser", existingUser);
            return "update-password";
        }

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        userService.save(existingUser);

        return "redirect:/getAllUser";
    }

    private boolean isUsernameOrEmailTaken(User updatedUser, Model model, int userId) {
        User userWithSameUsername = userRepository.findByUserName(updatedUser.getUserName());
        if (userWithSameUsername != null && !Objects.equals(userWithSameUsername.getUserId(), userId)) {
            model.addAttribute("error", "Username is already taken by another user.");
            return true;
        }

        User userWithSameEmail = userRepository.findByEmail(updatedUser.getEmail());
        if (userWithSameEmail != null && !Objects.equals(userWithSameEmail.getUserId(), userId)) {
            model.addAttribute("error", "Email is already taken by another user.");
            return true;
        }
        return false;
    }

    private String createUser(UserRegisterDTO userRegisterDTO, Model model, int roleId, String returnPage) {
        if (userService.existsByUserName(userRegisterDTO.getUserName())) {
            model.addAttribute("error", "Username already exists.");
            return returnPage;
        }

        if (userService.existsByEmail(userRegisterDTO.getEmail())) {
            model.addAttribute("error", "Email already exists.");
            return returnPage;
        }

        User user = new User();
        user.setUserName(userRegisterDTO.getUserName());
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setEmail(userRegisterDTO.getEmail());
        user.setDob(userRegisterDTO.getDob());
        user.setWallet(0.0f);


        Roles role = roleRepository.findById(roleId != 0 ? roleId : 1).orElse(null);
        if (role != null) {
            user.setRole(role);
        }

        Status status = new Status();
        status.setStatusID(1); // Assuming 1 is a valid status ID
        user.setStatus(status);

        userService.save(user);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    private String uploadAvatarFile(MultipartFile file) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.write(filePath, file.getBytes());
        return fileName;
    }
}