package com.se1858.G5.LandAuction.Entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Roles")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int roleID;

    @Enumerated(EnumType.STRING)
    ERole roleName;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

    public Roles(int roleID, ERole roleName) {
        this.roleID = roleID;
        this.roleName = roleName;
    }
}

