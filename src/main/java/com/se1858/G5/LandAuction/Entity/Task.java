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
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String task_name;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    private Date request_time;

    @ManyToOne
    @JoinColumn(name = "StatusID")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "AuctionID")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;
}
