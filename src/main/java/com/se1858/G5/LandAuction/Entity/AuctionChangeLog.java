package com.se1858.G5.LandAuction.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Auction_Change_Log")
public class AuctionChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private int log_id;

    @ManyToOne
    @JoinColumn(name = "AuctionID", nullable = false)
    private Auction auction;

    @Column(name = "action_type")
    private String action_type;

    @Column(name = "change_time")
    private Date change_time;

    @Column(name = "description")
    private String description;

    @Column(name = "reason")
    private String reason;

}
