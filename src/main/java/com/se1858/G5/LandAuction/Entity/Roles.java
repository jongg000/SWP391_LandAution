package com.se1858.G5.LandAuction.Entity;

import javax.persistence.*;
import java.util.Set;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
}

