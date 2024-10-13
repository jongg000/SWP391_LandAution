package com.se1858.G5.LandAuction.Entity;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToMany(mappedBy = "notification", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> user;
}
