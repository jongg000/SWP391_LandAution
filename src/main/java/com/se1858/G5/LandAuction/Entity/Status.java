package com.se1858.G5.LandAuction.Entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusID;
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String name;
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;


    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> user;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Auction> auction;

    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssetRegistration> assetRegistration;

}
