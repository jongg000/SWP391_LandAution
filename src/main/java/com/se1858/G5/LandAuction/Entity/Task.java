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
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TaskID")
    private int taskId;

    @Column(name = "task_name")
    private String task_name;

    @Column(name = "description")
    private String description;

    @Column(name = "request_time")
    private Date request_time;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "AuctionID")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;
}
