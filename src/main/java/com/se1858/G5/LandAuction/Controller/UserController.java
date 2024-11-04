package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.DTO.ProfileDTO;
import com.se1858.G5.LandAuction.DTO.UserRegisterDTO;
import com.se1858.G5.LandAuction.Entity.*;
import com.se1858.G5.LandAuction.Repository.*;
import com.se1858.G5.LandAuction.util.UploadFile;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

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

    @PostMapping("profile/change-password")
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


    @PostMapping("profile/edit")
    public String updateProfile(@ModelAttribute("profileDTO") ProfileDTO userProfileDTO,
//                                @RequestParam("nationalBackImage") MultipartFile nationalBackImage,
//                                @RequestParam("nationalFrontImage") MultipartFile nationalFrontImage,
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

//        UploadFile uploadFile = new UploadFile();
//        uploadFile.UploadImagesNationalF(nationalFrontImage, user);
//        uploadFile.UploadImagesNationalB(nationalBackImage, user);

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
        return "customer/profile";
    }



}