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
@Table(name = "Auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auctionId;

    private java.time.LocalDateTime  endTime;

    @ManyToOne
    @JoinColumn(name = "StatusID")
    private Status status;


    private java.time.LocalDateTime startTime;


    private long highestBid;

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
    private Set<Task> task;
}
