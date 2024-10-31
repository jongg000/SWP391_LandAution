package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT SUM(p.paymentAmount) FROM Payment p")
    Long getTotalPaymentAmount();
    @Query("SELECT p FROM Payment p WHERE p.paymentDate IS NOT NULL")
    List<Payment> findAllByOrderByPaymentDateAsc();
}

