package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.VNPayResponse;
import com.se1858.G5.LandAuction.Entity.Payment;

import javax.servlet.http.HttpServletRequest;
import java.time.Month;
import java.util.Map;

public interface PaymentService {
    public VNPayResponse createVnPayPayment(HttpServletRequest request, int orderTotal,  String vnPayReturnUrl);
    public int orderReturn(HttpServletRequest request);
    public void createPaymentBill(Payment payment);
    public long getTotalPaymentAmount();
    public Map<Month, Long> getMonthlyPaymentAmounts();
}
