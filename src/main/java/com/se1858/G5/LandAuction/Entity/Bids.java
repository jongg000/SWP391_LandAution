package com.se1858.G5.LandAuction.Entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "Bids")
public class Bids {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bidId;

    @ManyToOne
    @JoinColumn(name = "RegistrationID", nullable = false)
    private AuctionRegistration auctionRegistration;

    private long bidAmount;

    private java.time.LocalDateTime bidTime;
}

