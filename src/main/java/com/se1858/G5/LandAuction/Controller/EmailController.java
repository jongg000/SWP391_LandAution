package com.se1858.G5.LandAuction.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("email")
public class EmailController {

    private JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @RequestMapping("/send-mail")
    public String sendMail() {
       try {
           SimpleMailMessage message = new SimpleMailMessage();
           message.setFrom("springbootswp391@gmail.com");
           message.setTo("tungthptvixuyen@gmail.com");
           message.setSubject("Welcome to LandAuction");
           message.setText("Welcome to LandAuction");
           mailSender.send(message);
           return "customer/land-registratrion";
       } catch (Exception e) {
           return e.getMessage();
       }
    }
}
