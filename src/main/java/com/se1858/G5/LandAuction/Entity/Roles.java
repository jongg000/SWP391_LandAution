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
@Table(name = "Roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private int roleId;

    @Column(name = "Role_Name", nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Set<User> users;
}

