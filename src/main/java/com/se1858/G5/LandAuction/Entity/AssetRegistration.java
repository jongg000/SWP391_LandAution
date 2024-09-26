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
    private int documentId;

    @ManyToOne
    @JoinColumn(name = "landId", nullable = false)
    private Land land;

    @Column(nullable = false)
    private String registrationStatus;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(nullable = true)
    private java.util.Date approvalDate;

    @Column(columnDefinition = "TEXT")
    private String comments;
}
