package com.se1858.G5.LandAuction.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping("/home")  
    public String home() {
        return "home"; 
    }

    @GetMapping("/advanceSearchBody")  
    public String advanceSearchBody() {
        return "advanceSearchBody"; 
    }

    @GetMapping("/detail")  
    public String detail() {
        return "detail"; 
    }
}
