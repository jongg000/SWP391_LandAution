package com.se1858.G5.LandAuction.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "Asset_Registration")
public class AssetRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentId;

    @OneToOne
    @JoinColumn(name = "land_id")
    private Land land;

    @ManyToOne
    @JoinColumn(name = "StatusID")
    private Status status;

    private LocalDateTime registrationDate;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = true)
    private User user;

    @Column(nullable = true)
    private java.util.Date approvalDate;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String comments;

}
