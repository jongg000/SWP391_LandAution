package com.se1858.G5.LandAuction.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "Auction_Registration")
public class AuctionRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int registrationID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AuctionID", nullable = false)
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "auctionRegistration")
    private Set<Bids> bids;
}
