package com.se1858.G5.LandAuction.Entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AuctionID")
    private int auctionId;

    @Column(name = "end_time")
    private java.util.Date endTime;

    @Column(name = "Status", nullable = false)
    private String status;

    @Column(name = "start_time")
    private java.util.Date startTime;


    @Column(name = "highest_bid")
    private Float highestBid;

    @OneToMany(mappedBy = "auction")
    private Set<AuctionHistory> history;

    @ManyToOne
    @JoinColumn(name = "LandID", nullable = false)
    private Land land;

    @OneToMany(mappedBy = "auction")
    private Set<Bids> bids;

    @ManyToMany(mappedBy = "auctions", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users;
}
