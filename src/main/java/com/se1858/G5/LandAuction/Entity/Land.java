package com.se1858.G5.LandAuction.Entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Land")
public class Land {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LandID")
    private int landId;

    @Column(name = "Name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    @Column(name = "Location", columnDefinition = "TEXT")
    private String location;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Price")
    private Float price;

    @Column(name = "Contact")
    private String contact;

    @OneToMany(mappedBy = "land")
    private Set<Auction> auction;

    @OneToMany(mappedBy = "land", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LandImage> images;

    @OneToMany(mappedBy = "land")
    private Set<AssetRegistration> assetRegistration;



}

