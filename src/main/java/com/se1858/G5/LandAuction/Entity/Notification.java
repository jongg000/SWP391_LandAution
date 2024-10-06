package com.se1858.G5.LandAuction.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID")
    private int notificationId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;


    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;
}
