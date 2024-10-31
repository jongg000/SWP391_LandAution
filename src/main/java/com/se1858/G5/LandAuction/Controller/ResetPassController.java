package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.Entity.Token;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.TokenRepository;
import com.se1858.G5.LandAuction.Service.EmailService;
import com.se1858.G5.LandAuction.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResetPassController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenRepository tokenRepository;

    PasswordEncoder passwordEncoder;

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        User user = userService.findByEmail(email);
        if (user == null) {
            model.addAttribute("error", "Email không tồn tại!");
            return "forgot-password";
        }

        // Tạo token đặt lại mật khẩu
        String token = userService.createPasswordResetToken(user);
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;

        emailService.sendSimpleMail(email, "Reset Password", "Click the link to reset password: " + resetUrl);
        model.addAttribute("message", "Link đặt lại mật khẩu đã được gửi đến email của bạn.");
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam(value = "token", required = false) String token, Model model) {
        if (token == null || token.isEmpty()) {
            model.addAttribute("error", "Missing or invalid token!");
            return "reset-password";
        }
        Optional<Token> resetToken = tokenRepository.findByToken(token);
        if (resetToken.isEmpty() || resetToken.get().getExpiryDate().before(new Date())) {
            model.addAttribute("error", "Token không hợp lệ hoặc đã hết hạn!");
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }


    @PostMapping("/reset-password")
    public String processResetPassword(
            @RequestParam(name = "token", required = true) String token,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        if (token == null) {
            model.addAttribute("error", "Missing required token.");
            return "reset-password";
        }
        Token resetToken = tokenRepository.findByToken(token).orElse(null);
        if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
            model.addAttribute("error", "Token không hợp lệ hoặc đã hết hạn!");
            return "reset-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu xác nhận không khớp!");
            model.addAttribute("token", token);
            return "reset-password";
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);
        model.addAttribute("message", "Mật khẩu đã được thay đổi thành công!");
        tokenRepository.delete(resetToken); // Xóa token sau khi sử dụng
        return "login";
    }


}
