package com.se1858.G5.LandAuction.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
@Table(name = "Auction")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auctionId;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private java.time.LocalDateTime  endTime;

    @ManyToOne
    @JoinColumn(name = "StatusID")
    private Status status;
    private java.time.LocalDateTime startTime;
    private long highestBid;
    private java.time.LocalDateTime depositTime;
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

}
