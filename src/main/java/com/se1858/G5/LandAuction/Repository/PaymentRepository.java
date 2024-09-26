package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}

