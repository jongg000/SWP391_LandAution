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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DocumentID")
    private int documentId;

    @ManyToOne
    @JoinColumn(name = "LandID", nullable = false)
    private Land land;

    @ManyToOne
    @JoinColumn(name = "StatusID")
    private Status status;

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
