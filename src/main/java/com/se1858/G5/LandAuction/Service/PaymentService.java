package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.VNPayResponse;
import com.se1858.G5.LandAuction.Entity.Payment;
import com.se1858.G5.LandAuction.Entity.User;

import javax.servlet.http.HttpServletRequest;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface PaymentService {
    public VNPayResponse createVnPayPayment(HttpServletRequest request, long orderTotal,  String vnPayReturnUrl);
    public int orderReturn(HttpServletRequest request);
    public void createPaymentBill(Payment payment);
    public long getTotalPaymentAmount();
    public Map<Month, Long> getMonthlyPaymentAmounts();
    public long getTotalPaymentsForMonth(YearMonth yearMonth);
    public Map<Integer, Long> getMonthlyRevenue();
    public List<Payment> getByUser(User user);
}
