package com.se1858.G5.LandAuction.Entity;

import javax.persistence.*;
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
    private int log_id;

    @ManyToOne
    @JoinColumn(name = "AuctionID", nullable = false)
    private Auction auction;

    private String action_type;

    private Date change_time;

    private String description;

    private String reason;

}
