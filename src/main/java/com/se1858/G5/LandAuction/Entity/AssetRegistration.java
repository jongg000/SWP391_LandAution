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
@Table(name = "Asset_Registration")
public class AssetRegistration {
    @Id
    @Column(name = "DocumentID")
    private int documentId;

    @ManyToOne
    @JoinColumn(name = "LandID", nullable = false)
    private Land land;

    @Column(name = "Registration_Status",nullable = false)
    private String registrationStatus;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(name = "Approval_Date", nullable = true)
    private java.util.Date approvalDate;

    @Column(name = "Comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "Path")
    private String path;
}
