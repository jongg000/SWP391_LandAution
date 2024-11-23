package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.VNPayResponse;
import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Payment;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Service.*;
import com.se1858.G5.LandAuction.Service.ServiceImpl.AuctionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("payment")
public class PaymentController {
    private  AuctionService auctionService;
    private PaymentService paymentService;
    public UserService userService;
    public AssetRegistrationService assetRegistrationService;
    public StatusService statusService;
    private EmailService emailService;

    @Autowired
    public PaymentController(AuctionService auctionService, PaymentService paymentService, UserService userService, AssetRegistrationService assetRegistrationService, StatusService statusService, EmailService emailService) {
        this.auctionService = auctionService;
        this.paymentService = paymentService;
        this.userService = userService;
        this.assetRegistrationService = assetRegistrationService;
        this.statusService = statusService;
        this.emailService = emailService;
    }

    @GetMapping("/user")
    public String handle(Model model, Principal principal) {
            String username = principal.getName();
            User user = userService.findByEmail(username);
            model.addAttribute("user", user);
            return "/customer/display";
    }
    //dẫn đến đường link thanh toán
    @RequestMapping("handle/{id}")
    public String handle(HttpServletRequest request, @PathVariable int id) {
        int amount  = 500000;
        VNPayResponse vnPayResponse = paymentService.createVnPayPayment(request, amount,"http://localhost:8080/payment/back/"+id);
        String link =vnPayResponse.paymentUrl;
        return "redirect:" + link;
    }

    @RequestMapping("handleafter/{id}")
    public String handleAfter(HttpServletRequest request, @PathVariable int id) {
        long amount = (long) Math.ceil(auctionService.findAuctionById(id).getHighestBid() * 0.5);;
        VNPayResponse vnPayResponse = paymentService.createVnPayPayment(request, amount,"http://localhost:8080/payment/back1/"+id);
        String link =vnPayResponse.paymentUrl;
        return "redirect:" + link;
    }
    @RequestMapping(value = "/back1/{id}", method = RequestMethod.GET)
    public String paymentAfterCompleted(HttpServletRequest request, Model model, Principal principal,@PathVariable int id) {
        int paymentStatus =paymentService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String username = principal.getName();
        User user = userService.findByEmail(username);
        String paymentInformation = orderInfo + "-" + transactionId;
        Payment payment = new Payment(user, paymentInformation, Long.parseLong(totalPrice), LocalDateTime.now());
        paymentService.createPaymentBill(payment);
        if(paymentStatus == 1) {
            emailService.sendSimpleMail(user.getEmail(), "Thanh toán thành công", "Bạn đã thanh toán thành công "+totalPrice+" VND. Hẹn gặp bạn ở ĐH FPT ngày xx/xx/xxxx!!");
        }
        return paymentStatus == 1 ? "redirect:/auction/save/" + id : "redirect:/auction/showAuctionDetail/" + id;

    }
    //Thanh toan phi dang ki tai san
    @GetMapping("registration-fee/{id}")
    public String registrationFee(HttpServletRequest request, @PathVariable("id") int doucmunentid) {
        int amount  = 500000;
        VNPayResponse vnPayResponse = paymentService.createVnPayPayment(request, amount,"http://localhost:8080/payment/back-top-page/"+doucmunentid);
        String link =vnPayResponse.paymentUrl;
        return "redirect:" + link;
    }

    //Sau khi thanh toán thành công
    @RequestMapping(value = "/back-top-page/{id}", method = RequestMethod.GET)
    public String paymentRegistration(HttpServletRequest request, Model model, Principal principal,@PathVariable int id) {
        int paymentStatus =paymentService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String username = principal.getName();
        User user = userService.findByEmail(username);
        AssetRegistration assetRegistration = assetRegistrationService.getAssetRegistrationByID(id);
        String paymentInformation = "";
        if(assetRegistration.getStatus().getStatusID() == 17){
            paymentInformation = "Đăng kí đấu giá lại tài sản: "+ assetRegistration.getDocumentId() + " " +orderInfo + " " + paymentTime + " " + transactionId;
            assetRegistration.setStatus(statusService.getStatusById(16));
        }else {
            paymentInformation = "Đăng kí tài sản: " + assetRegistration.getDocumentId() + " " + orderInfo + " " + paymentTime + " " + transactionId;
            assetRegistration.setStatus(statusService.getStatusById(4));
        }
        assetRegistrationService.updateAssetRegistration(assetRegistration);
        Payment payment = new Payment(user, paymentInformation, Long.parseLong(totalPrice), LocalDateTime.now());
        paymentService.createPaymentBill(payment);
        return paymentStatus == 1 ? "redirect:/asset/asset-detail/" + id : "redirect:/asset";
    }

    @RequestMapping(value = "/back/{id}", method = RequestMethod.GET)
    public String paymentCompleted(HttpServletRequest request, Model model, Principal principal,@PathVariable int id) {
        int paymentStatus =paymentService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String username = principal.getName();
        User user = userService.findByEmail(username);
        String paymentInformation = "Thanh toán" + " " +orderInfo + " " + paymentTime + " " + transactionId;
        Payment payment = new Payment(user, paymentInformation, Long.parseLong(totalPrice), LocalDateTime.now());
        paymentService.createPaymentBill(payment);
        if(paymentStatus == 1) {
            emailService.sendSimpleMail(user.getEmail(), "Đặt cọc thành công", "Bạn đã đặt cọc thành công "+totalPrice+" VND. Bạn đã tham gia phiên đấu giá!!");
        }
        return paymentStatus == 1 ? "redirect:/auctionRegistration/save/" + id : "redirect:/auction/showAuctionDetail/" + id;

    }
}
