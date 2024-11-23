package com.se1858.G5.LandAuction.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;
    private long paymentAmount;

    @Column(nullable = true)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime paymentDate;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String paymentDescription;

    public Payment(User user, String paymentDescription, long paymentAmount , LocalDateTime paymentDate) {
        this.user = user;
        this.paymentDescription = paymentDescription;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
    }
}
