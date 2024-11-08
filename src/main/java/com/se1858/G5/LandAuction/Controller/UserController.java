package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.DTO.CancelAssetDTO;
import com.se1858.G5.LandAuction.DTO.ProfileDTO;
import com.se1858.G5.LandAuction.DTO.UserRegisterDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.*;
import com.se1858.G5.LandAuction.Service.*;
import com.se1858.G5.LandAuction.util.UploadFile;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;

@Controller
@RequestMapping()
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    RolesRepository roleRepository;
    private final StatusRepository statusRepository;
    AssetRegistrationService assetRegistrationService;
    StatusService statusService;
    ViolationService violationService;
    AuctionService auctionService;
    EmailService emailService;

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
        status.setStatusID(14); // Ví dụ: 1 là trạng thái "Active" hoặc tương đương
        user.setStatus(status);

        // Lưu người dùng vào cơ sở dữ liệu
        userService.save(user);
    }
    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // Lấy thông tin người dùng hiện tại từ tên đăng nhập (email)
        String email = principal.getName();
        User user = userService.findByEmail(email);
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFirstName(user.getFirstName());
        profileDTO.setLastName(user.getLastName());
        profileDTO.setPhoneNumber(user.getPhoneNumber());
        profileDTO.setAddress(user.getAddress());
        profileDTO.setEmail(email);
        profileDTO.setGender(user.getGender());
        profileDTO.setNationalID(user.getNationalID());
        profileDTO.setDob(user.getDob());

        model.addAttribute("profileDTO",profileDTO);
        // Đưa thông tin người dùng vào model
        model.addAttribute("user", user);

        // Trả về trang profile
        return "customer/profile";
    }

    @PostMapping("/profile/change-password")
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
            return "customer/profile";
        }

        // Kiểm tra nếu mật khẩu mới giống với mật khẩu hiện tại
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu mới không được giống mật khẩu hiện tại.");
            model.addAttribute("user", user);
            return "customer/profile";
        }

        // Kiểm tra xem mật khẩu mới có khớp với xác nhận mật khẩu không
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
            model.addAttribute("user", user);
            return "customer/profile";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);

        model.addAttribute("success", "Mật khẩu đã được cập nhập.");
        model.addAttribute("user", user);
        return "customer/profile";
    }


    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute("profileDTO") ProfileDTO userProfileDTO,
                                Principal principal, Model model){

        String email = principal.getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "customer/profile";
        }
        // Kiểm tra email và số điện thoại tồn tại trước khi tạo người dùng mới
        if (userService.existsByEmail(userProfileDTO.getEmail())) {
            model.addAttribute("emailError", "Email này đã tồn tại.");
            model.addAttribute("user", user);
            return "customer/profile";
        }

        if (userService.existsByPhoneNumber(userProfileDTO.getPhoneNumber())) {
            model.addAttribute("error", "Số điện thoại đã tồn tại.");
            model.addAttribute("user", user);
            return "customer/profile";
        }

        // Update personal information
        user.setFirstName(userProfileDTO.getFirstName());
        user.setLastName(userProfileDTO.getLastName());
        user.setPhoneNumber(userProfileDTO.getPhoneNumber());
        user.setAddress(userProfileDTO.getAddress());
        user.setDob(userProfileDTO.getDob());
        user.setGender(userProfileDTO.getGender());
        user.setEmail(userProfileDTO.getEmail());

    // Chỉ kiểm tra nếu National ID khác nhau và không phải là null
        if (userProfileDTO.getNationalID() != null &&
                (user.getNationalID() == null || !user.getNationalID().equals(userProfileDTO.getNationalID())) &&
                userService.existsByNationalID(userProfileDTO.getNationalID())) {

            model.addAttribute("error", "Số CMND đã tồn tại.");
            model.addAttribute("user", user);
            return "customer/profile";
        } else {
            user.setNationalID(userProfileDTO.getNationalID());
        }
        user.setStatus(statusRepository.getById(1));
        // Save updated user information to the database
        userService.save(user);

        model.addAttribute("success", "Cập nhật thông tin cá nhân thành công.");
        model.addAttribute("user", user);
        return "redirect:/profile";
    }

    @PostMapping("/profile/upload")
    public String upAvatar(@RequestParam("avatar") MultipartFile images,
                           RedirectAttributes redirectAttributes, Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        UploadFile uploadFile = new UploadFile();
        uploadFile.upLoadDocumentAvata(images, user);
        userService.save(user);
        model.addAttribute("user", user);
        return "redirect:/profile";
    }

    @PostMapping("/profile/uploadNational")
    public String upNation(@RequestParam("nationalBackImage") MultipartFile nationalBackImage,
                           @RequestParam("nationalFrontImage") MultipartFile nationalFrontImage,
                           RedirectAttributes redirectAttributes, Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        UploadFile uploadFile = new UploadFile();
        uploadFile.UploadImagesNationalF(nationalFrontImage, user);
        uploadFile.UploadImagesNationalB(nationalBackImage, user);
        userService.save(user);
        model.addAttribute("user", user);
        return "redirect:/profile";
    }

    @GetMapping("/user-drop/{id}")
    public String cancel(Model model, @PathVariable int id) {
        AssetRegistration assetRegistration = assetRegistrationService.getAssetRegistrationByID(id);
        CancelAssetDTO cancelAssetDTO = new CancelAssetDTO();
        cancelAssetDTO.setId(id);
        cancelAssetDTO.setDate(assetRegistration.getRegistrationDate());
        cancelAssetDTO.setName(assetRegistration.getLand().getName());
        model.addAttribute("cancelAssetDTO", cancelAssetDTO);
        return "customer/cancel-form";
    }

    @PostMapping("/handle-cancel")
    public String handleCancel(@ModelAttribute("cancelAssetDTO") CancelAssetDTO cancelAssetDTO, Principal principal, Model model) {
        User user = userService.findByEmail(principal.getName());
        AssetRegistration assetRegistration = assetRegistrationService.getAssetRegistrationByID(cancelAssetDTO.getId());
        assetRegistration.setReason(cancelAssetDTO.getReason());
        assetRegistration.setStatus(statusService.getStatusById(9));
        Violation violation = new Violation();
        violation.setUser(user);
        Auction auction = auctionService.findAuctionByLand(assetRegistration.getLand());
        String detail = "";
        if(auction != null) {
             detail = "Hủy bỏ tài sản " + assetRegistration.getLand().getName()+ " Cấp độ 3";
            Set<User> userList = auction.getUser();
            if(userList != null) {
                for(User item : userList) {
                    BigDecimal balance = item.getRefundMoney().add(new BigDecimal("500000"));
                    item.setRefundMoney(balance);
                    emailService.sendSimpleMail(item.getEmail(), "THÔNG BÁO HỦY BUỔI ĐẤU THẦU", "Chúng tôi thành thật xin lỗi vì sự bất tiện khi buổi đấu giá ngày [ngày dự kiến] đã bị hủy. Do một số lý do ngoài ý muốn, sự kiện không thể diễn ra như dự kiến. ");
                }
            }
        }else{
             detail = "Hủy bỏ tài sản " + assetRegistration.getLand().getName()+ " Cấp độ 2";
        }
        auction.setStatus(statusService.getStatusById(9));
        auction.setStartTime(null);
        auction.setEndTime(null);
        violation.setDetail(detail);
        violationService.saveViolation(violation);
        assetRegistrationService.save(assetRegistration);
        auctionService.saveAuction(auction);
        return "redirect:/asset";
    }



}