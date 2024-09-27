package com.se1858.G5.LandAuction.Entity;

import jakarta.persistence.*;
import java.util.Date;
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
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private int userId;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Phone_Number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "Avatar")
    private String avatar;

    @Column(name = "Status")
    private String status; // unverified, verified, or ban

    @Column(name = "Wallet")
    private Float wallet;

    @Column(name = "National_Front_Image", nullable = true)
    private String nationalFrontImage;

    @Column(name = "National_Back_Image",nullable = true)
    private String nationalBackImage;

    @Column(name = "NationalID", nullable = true, unique = true)
    private Integer nationalID;

    @Temporal(TemporalType.DATE)
    @Column(name = "Dob")
    private Date dob; // Date of Birth

    @ManyToOne
    @JoinColumn(name = "RoleID")
    private Roles role; // Liên kết đến bảng Roles

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Land> lands;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Documents> documents;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "User_Auction_Participation",
            joinColumns = @JoinColumn(name = "UserID"),
            inverseJoinColumns = @JoinColumn(name = "AuctionID")
    )
    private Set<Auction> auctions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notification> notifications;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Wishlist> wishlists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AssetRegistration> assetRegistrations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuctionRegistration> auctionRegistrations;

}

