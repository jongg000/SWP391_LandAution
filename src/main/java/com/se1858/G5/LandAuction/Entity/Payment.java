package com.se1858.G5.LandAuction.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    private long paymentAmount;

    @Column(nullable = true)
    private LocalDateTime paymentDate;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String paymentDescription;

    public Payment(User user, String paymentDescription, long    paymentAmount) {
        this.user = user;
        this.paymentDescription = paymentDescription;
        this.paymentAmount = paymentAmount;
    }
}
