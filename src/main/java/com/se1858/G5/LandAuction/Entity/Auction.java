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
    private int auctionId;

    @ManyToOne
    @JoinColumn(name = "landId", nullable = false)
    private Land land;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = false)
    private String status;

    private java.util.Date startTime;

    private java.util.Date endTime;

    private Float highestBid;

    @OneToMany(mappedBy = "auction")
    private Set<AuctionHistory> history;
}
