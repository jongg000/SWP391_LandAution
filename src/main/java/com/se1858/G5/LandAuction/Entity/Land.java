package com.se1858.G5.LandAuction.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Land")
public class Land {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int landId;

    @Column( nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn( nullable = false)
    private User user;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String location;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    private Float price;

    private String contact;

    private String path;

    @OneToMany(mappedBy = "land")
    private Set<Auction> auction;

    @OneToMany(mappedBy = "land", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LandImage> images;

    @OneToOne(mappedBy = "land")
    private AssetRegistration assetRegistration;



}

