package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.ERole;
import com.se1858.G5.LandAuction.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    Optional<Roles> findByRoleName(ERole roleName);
}
