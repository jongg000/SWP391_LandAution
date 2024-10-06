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

    @ManyToOne
    @JoinColumn(name = "StatusID")
    private Status status;

    @Column(name = "start_time")
    private java.util.Date startTime;


    @Column(name = "Highest_Bid")
    private Float highestBid;

    @ManyToOne
    @JoinColumn(name = "LandID", nullable = false)
    private Land land;

    @OneToMany(mappedBy = "auction")
    private Set<AuctionRegistration> auctionRegistration;

    @OneToMany(mappedBy = "auction")
    private Set<Wishlist> wishlist;

    @ManyToMany(mappedBy = "auction", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> user;

    @OneToMany(mappedBy = "auction")
    private Set<AuctionChangeLog> auctionChangeLog;

    @OneToMany(mappedBy = "auction")
    private Set<Payment> payment;

    @OneToMany(mappedBy = "auction")
    private Set<Task> task;
}
