package com.se1858.G5.LandAuction.Entity;

import javax.persistence.*;
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
    private int landId;

    @Column( nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn( nullable = false)
    private User user;

    @Column( columnDefinition = "TEXT")
    private String location;

    @Column( columnDefinition = "TEXT")
    private String description;

    private Float price;

    private String contact;

    @OneToMany(mappedBy = "land")
    private Set<Auction> auction;

    @OneToMany(mappedBy = "land", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LandImage> images;

    @OneToMany(mappedBy = "land")
    private Set<AssetRegistration> assetRegistration;



}

