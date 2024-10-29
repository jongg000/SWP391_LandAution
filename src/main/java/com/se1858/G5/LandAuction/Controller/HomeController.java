package com.se1858.G5.LandAuction.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home"; // Chuyển hướng đến trang home
    }

    @GetMapping("/home1")
    public String showHomePage(Model model) {
        return "home"; // Trả về tên của file HTML home.html
    }
}
