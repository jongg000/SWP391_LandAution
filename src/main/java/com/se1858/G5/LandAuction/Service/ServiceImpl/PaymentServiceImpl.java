package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Config.VNPayConfig;
import com.se1858.G5.LandAuction.DTO.VNPayResponse;
import com.se1858.G5.LandAuction.Entity.Payment;
import com.se1858.G5.LandAuction.Repository.PaymentRepository;
import com.se1858.G5.LandAuction.Service.PaymentService;
import com.se1858.G5.LandAuction.util.VNPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private VNPayConfig vnPayConfig;

    @Autowired
    private VNPayUtil vnPayUtil;

    private PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public VNPayResponse createVnPayPayment(HttpServletRequest request, long orderTotal, String vnPayReturnUrl) {

        Map<String, String> vnpParamsMap;
        vnpParamsMap = vnPayConfig.getVNPayConfig(vnPayReturnUrl);


        vnpParamsMap.put("vnp_Amount", String.valueOf((long) orderTotal * 100));
        vnpParamsMap.put("vnp_IpAddr", vnPayUtil.getIpAddress(request));

        // Build query URL
        String queryUrl = vnPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = vnPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = vnPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnpPayUrl() + "?" + queryUrl;
        VNPayResponse vnPayResponse = new VNPayResponse("ok", "success", paymentUrl);
        return vnPayResponse;
    }

    @Override
    public int orderReturn(HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = null;
            String fieldValue = null;
            try {
                fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }


        String signValue = vnPayUtil.hashAllFields(fields);

        // so sánh vnp_SecureHash và signValue có trùng nhau ?
        if (signValue.equals(vnp_SecureHash)) {
            System.out.println("in compare");
            if ("00".equals(request.
                    getParameter("vnp_TransactionStatus"))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }

    @Override
    public void createPaymentBill(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public long getTotalPaymentAmount() {
        Long totalAmount = paymentRepository.getTotalPaymentAmount();
        return (totalAmount != null) ? totalAmount : 0;
    }

    @Override
    public Map<Month, Long> getMonthlyPaymentAmounts() {
        List<Payment> payments = paymentRepository.findAllByOrderByPaymentDateAsc();
        return payments.stream()
                .collect(Collectors.groupingBy(
                        payment -> payment.getPaymentDate().getMonth(),
                        Collectors.summingLong(Payment::getPaymentAmount)
                ));
    }

    @Override
    public long getTotalPaymentsForMonth(YearMonth yearMonth) {
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
        return paymentRepository.calculateTotalPaymentsForPeriod(startOfMonth, endOfMonth);
    }

    @Override
    public Map<Integer, Long> getMonthlyRevenue() {
        List<Payment> payments = paymentRepository.findAll();
        Map<Integer, Long> monthlyRevenue = new HashMap<>();

        for (Payment payment : payments) {
            int month = payment.getPaymentDate().getMonthValue(); // Get month as integer (1 = Jan, etc.)
            monthlyRevenue.put(month, monthlyRevenue.getOrDefault(month, 0L) + payment.getPaymentAmount());
        }

        return monthlyRevenue;
    }
}
