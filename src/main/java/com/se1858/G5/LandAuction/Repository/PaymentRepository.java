package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Payment;
import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT SUM(p.paymentAmount) FROM Payment p")
    Long getTotalPaymentAmount();
    @Query("SELECT p FROM Payment p WHERE p.paymentDate IS NOT NULL")
    List<Payment> findAllByOrderByPaymentDateAsc();
    List<Payment> findByUser(User user);
    List<Payment> findByPaymentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT COALESCE(SUM(p.paymentAmount), 0) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    long calculateTotalPaymentsForPeriod(LocalDateTime startDate, LocalDateTime endDate);
}

