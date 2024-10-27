package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.VNPayResponse;
import com.se1858.G5.LandAuction.Entity.Payment;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Service.PaymentService;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    private PaymentService paymentService;
    public UserService userService;

    public PaymentController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping
    public String handle(Model model, Principal principal) {
            String username = principal.getName();
            User user = userService.findByEmail(username);
            model.addAttribute("user", user);
            return "/customer/display";
    }
    //dẫn đến đường link thanh toán
    @RequestMapping("/handle")
    public String handle(HttpServletRequest request) {
        int amount  = 500000;
        VNPayResponse vnPayResponse = paymentService.createVnPayPayment(request, amount,"http://localhost:8080/payment/back");
        String link =vnPayResponse.paymentUrl;
        return "redirect:" + link;
    }
    //Sau khi thanh toán thành công
    @RequestMapping(value = "/back", method = RequestMethod.GET)
    public String paymentCompleted(HttpServletRequest request, Model model, Principal principal) {
        int paymentStatus =paymentService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String username = principal.getName();
        User user = userService.findByEmail(username);
        String paymentInformation = "Thanh toán" + " " +orderInfo + " " + paymentTime + " " + transactionId;
        Payment payment = new Payment(user, paymentInformation, Long.parseLong(totalPrice));
        paymentService.createPaymentBill(payment);
        return paymentStatus == 1 ? "/customer/Success" : "customer/Fail";
    }
}
