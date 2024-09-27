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
    @Column(name = "DocumentID")
    private int documentId;

    @ManyToOne
    @JoinColumn(name = "AuctionID", nullable = false)
    private Auction auction;

    @Column(name = "RegistrationStatus")
    private String registrationStatus;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(name = "ApprovalDate")
    private java.util.Date approvalDate;

    @Column(name = "Comments")
    private String comments;
}
