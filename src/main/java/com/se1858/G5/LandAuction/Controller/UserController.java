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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping
public class UserController {
    UserService userService;
    PasswordEncoder passwordEncoder;
    RolesRepository roleRepository;
    StatusRepository statusRepository;
    EmailService emailService;
    AssetRegistrationService assetRegistrationService;
    StatusService statusService;
    ViolationService violationService;
    AuctionService auctionService;
    PaymentService paymentService;
    AuctionRegistrationService auctionRegistrationService;

    @Autowired
    public UserController(AuctionRegistrationService auctionRegistrationService, UserService userService, PaymentService paymentService, PasswordEncoder passwordEncoder, RolesRepository roleRepository, StatusRepository statusRepository, EmailService emailService, AssetRegistrationService assetRegistrationService, StatusService statusService, ViolationService violationService, AuctionService auctionService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.emailService = emailService;
        this.assetRegistrationService = assetRegistrationService;
        this.statusService = statusService;
        this.violationService = violationService;
        this.auctionService = auctionService;
        this.paymentService = paymentService;
        this.auctionRegistrationService = auctionRegistrationService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDTO", new UserRegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerDTO") UserRegisterDTO userRegisterDTO, BindingResult bindingResult, HttpSession session, Model model) {
        // Kiểm tra email và số điện thoại tồn tại
        if (userService.existsByEmail(userRegisterDTO.getEmail())) {
            model.addAttribute("emailError", "Email này đã tồn tại.");
            return "register";
        }
        if (userService.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())) {
            model.addAttribute("phoneError", "Số điện thoại đã tồn tại.");
            return "register";
        }

        // Sinh OTP
        String otp = emailService.generateOtp();
        emailService.sendOtpEmail(userRegisterDTO.getEmail(), otp);

        // Lưu OTP và thông tin người dùng vào session
        session.setAttribute("otp", otp);
        session.setAttribute("userRegisterDTO", userRegisterDTO);
        return "otp_verification";
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") String otp, HttpSession session, Model model) {
        String sessionOtp = (String) session.getAttribute("otp");
        UserRegisterDTO userRegisterDTO = (UserRegisterDTO) session.getAttribute("userRegisterDTO");

        if (sessionOtp != null && sessionOtp.equals(otp)) {
            // Tạo người dùng sau khi OTP xác nhận thành công
            createUser(userRegisterDTO, 1);
            model.addAttribute("success", "Đăng ký thành công!");

            // Xóa OTP và thông tin người dùng khỏi session sau khi thành công
            session.removeAttribute("otp");
            session.removeAttribute("userRegisterDTO");

            return "redirect:/login";
        } else {
            model.addAttribute("otpError", "OTP không hợp lệ.");
            return "otp_verification";
        }
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

        model.addAttribute("profileDTO", profileDTO);
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
        return "redirect:/profile";
    }


    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute("profileDTO") ProfileDTO userProfileDTO,
                                Principal principal, Model model) {

        String email = principal.getName();
        User user = userService.findByEmail(email);

        if (!user.getPhoneNumber().equalsIgnoreCase(userProfileDTO.getPhoneNumber())) {
            if (userService.existsByPhoneNumber(userProfileDTO.getPhoneNumber())) {
                model.addAttribute("error", "Số điện thoại đã tồn tại.");
                return "customer/profile";
            } else  {
                user.setPhoneNumber(userProfileDTO.getPhoneNumber());
            }
        }
        if (user.getNationalID() != null) {
            if (!user.getNationalID().equalsIgnoreCase(userProfileDTO.getNationalID())) {
                if (userService.existsByNationalID(userProfileDTO.getNationalID())) {
                    model.addAttribute("error", "Số CMND đã tồn tại.");
                    model.addAttribute("user", user);
                    return "customer/profile";
                }
                else  {
                    user.setNationalID(userProfileDTO.getNationalID());
                }
            }
        } else {
            if (userService.existsByNationalID(userProfileDTO.getNationalID())) {
                model.addAttribute("error", "Số CMND đã tồn tại.");
                model.addAttribute("user", user);
                return "customer/profile";
            }
            else  {
                user.setNationalID(userProfileDTO.getNationalID());
            }
        }

        // Update personal information
        user.setFirstName(userProfileDTO.getFirstName());
        user.setLastName(userProfileDTO.getLastName());
        user.setAddress(userProfileDTO.getAddress());
        user.setDob(userProfileDTO.getDob());
        user.setGender(userProfileDTO.getGender());
        user.setEmail(userProfileDTO.getEmail());
        user.setStatus(statusRepository.getById(4));
        // Save updated user information to the database
        userService.save(user);
        model.addAttribute("success", "Cập nhật thông tin cá nhân thành công. Chờ xét duyêt hãy chờ email");
        model.addAttribute("user", user);
        return "customer/profile";

    }

    @PostMapping("/profile/upload")
    public String upAvatar(@RequestParam("avatar") MultipartFile images,
                           Principal principal, Model model) {
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
                           Principal principal, Model model) {
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
        if (auction != null) {
            detail = "Hủy bỏ tài sản " + assetRegistration.getLand().getName() + " Cấp độ 3";
            List<User> userList = auctionRegistrationService.getUserInAuction(auction);
            System.out.println(userList);;
            if (userList != null) {
                for (User item : userList) {
                    BigDecimal balance = item.getRefundMoney().add(new BigDecimal("500000"));
                    item.setRefundMoney(balance);
                    userService.save(item);
                    emailService.sendSimpleMail(item.getEmail(), "THÔNG BÁO HỦY BUỔI ĐẤU THẦU", "Chúng tôi thành thật xin lỗi vì sự bất tiện khi buổi đấu giá ngày [ngày dự kiến] đã bị hủy. Do một số lý do ngoài ý muốn, sự kiện không thể diễn ra như dự kiến. ");
                }
            }
        } else {
            detail = "Hủy bỏ tài sản " + assetRegistration.getLand().getName() + " Cấp độ 2";
        }
        auction.setStatus(statusService.getStatusById(9));
        violation.setDetail(detail);
        violationService.saveViolation(violation);
        assetRegistrationService.save(assetRegistration);
        auctionService.saveAuction(auction);
        return "redirect:/asset";
    }

    @GetMapping("payment-history")
    public String paymentHistory(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        List<Payment> payments = paymentService.getByUser(user);
        BigDecimal refundMoney = user.getRefundMoney();
        model.addAttribute("refund", refundMoney);
        long total = 0;
        for (Payment payment : payments) {
            total += payment.getPaymentAmount();
        }
        model.addAttribute("total", total);
        model.addAttribute("payments", payments);
        return "/customer/payment-history";
    }




}