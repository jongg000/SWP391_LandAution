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
@Table(name = "Notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;

    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String content;

    @ManyToMany(mappedBy = "notification", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> user;
}
