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
@Table(name = "Auction_Registration")
public class AuctionRegistration {
    @Id
    private int documentId;

    @ManyToOne
    @JoinColumn(name = "auctionId", nullable = false)
    private Auction auction;

    private String registrationStatus;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    private java.util.Date approvalDate;

    private String comments;
}
