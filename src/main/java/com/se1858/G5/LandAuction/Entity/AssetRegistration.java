package com.se1858.G5.LandAuction.Entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
@Table(name = "Asset_Registration")
public class AssetRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int documentId;

    @OneToOne
    @JoinColumn(name = "LandID", nullable = false)
    private Land land;

    @ManyToOne
    @JoinColumn(name = "StatusID")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(nullable = true)
    private java.util.Date approvalDate;

    @Column(columnDefinition = "NVARCHAR(255)")
    private String comments;

    @Column(nullable = true)
    private LocalDateTime registrationDate;

    @Column(columnDefinition = "NVARCHAR(255)", nullable = true)
    private String reason;

    public AssetRegistration(User user) {
        this.user = user;
    }
}
