package com.se1858.G5.LandAuction.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentID")
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @Column(name = "Payment_Amount")
    private float paymentAmount;

    @Column(name="Payment_Status")
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "AuctionID", nullable = false)
    private Auction auction;
}
