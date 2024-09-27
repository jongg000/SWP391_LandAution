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
@Table(name = "Bids")
public class Bids {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidID")
    private int bidId;

    @ManyToOne
    @JoinColumn(name = "AuctionID", nullable = false)
    private Auction auction;

    @Column(name = "Bid_Amount")
    private float bidAmount;

    @Column(name = "Bid_Time")
    private java.util.Date bidTime;
}

