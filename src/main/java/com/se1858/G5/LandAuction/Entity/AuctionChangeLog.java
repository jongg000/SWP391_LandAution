package com.se1858.G5.LandAuction.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "Auction_Change_Log")
public class AuctionChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int log_id;

    @ManyToOne
    @JoinColumn(name = "AuctionID", nullable = false)
    private Auction auction;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String action_type;

    private Date change_time;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String reason;

}
