package com.se1858.G5.LandAuction.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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

    @Column( columnDefinition = "TEXT")
    private String comments;
    @Column(name = "Path")
    private String path;

}
