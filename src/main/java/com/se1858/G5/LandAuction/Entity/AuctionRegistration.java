package com.se1858.G5.LandAuction.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Auction_Registration")
public class AuctionRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int registrationID;

    @ManyToOne
    @JoinColumn(name = "AuctionID", nullable = false)
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "StatusID")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "auctionRegistration")
    private Set<Bids> bids;
}
