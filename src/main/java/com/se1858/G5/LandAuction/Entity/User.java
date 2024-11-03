package com.se1858.G5.LandAuction.Entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String firstName;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phoneNumber;

    private String avatar;

    @Column(name = "refund_money", nullable = false, columnDefinition = "NUMERIC(19,0) DEFAULT 0")
    private BigDecimal refundMoney = BigDecimal.ZERO;

    private String nationalID;
    private String gender;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String address;
    private String nationalFrontImage;
    private String nationalBackImage;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dob;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StatusID")
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoleID")
    private Roles role;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "User_Auction_Participation",
            joinColumns = @JoinColumn(name = "UserID"),
            inverseJoinColumns = @JoinColumn(name = "AuctionID")
    )
    private Set<Auction> auction;
}

