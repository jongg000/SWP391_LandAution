package com.se1858.G5.LandAuction.Entity;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Bids")
public class Bids {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidID")
    private int bidId;

    @ManyToOne
    @JoinColumn(name = "RegistrationID", nullable = false)
    private AuctionRegistration auctionRegistration;

    @Column(name = "bid_amount")
    private float bidAmount;

    @Column(name = "bid_time")
    private java.util.Date bidTime;
}

