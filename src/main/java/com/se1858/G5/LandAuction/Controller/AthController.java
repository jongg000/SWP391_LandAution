package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.Request.UserRegisterRequest;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new UserRegisterRequest());
        return "Customer/register";
    }

    @PostMapping("/register")
    public String register(UserRegisterRequest userRegisterRequest) {
        userService.registerUser(userRegisterRequest);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin(Model model) {
        return "Customer/login";
    }

    @PostMapping("/login")
        public String login(String username, String password, Model model) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );
                return "Successfully";
            } catch (AuthenticationException e) {
                model.addAttribute("error", "Invalid username or password.");
                return "Customer/login";
            }
        }
}
