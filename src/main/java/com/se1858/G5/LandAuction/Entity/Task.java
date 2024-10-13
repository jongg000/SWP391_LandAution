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
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    private String task_name;

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
