package com.se1858.G5.LandAuction.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // Tên file HTML là 403.html trong thư mục templates
    }

}
