package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.VNPayResponse;
import com.se1858.G5.LandAuction.Entity.Payment;

import javax.servlet.http.HttpServletRequest;

public interface PaymentService {
    public VNPayResponse createVnPayPayment(HttpServletRequest request, long orderTotal,  String vnPayReturnUrl);
    public int orderReturn(HttpServletRequest request);
    public void createPaymentBill(Payment payment);
}
