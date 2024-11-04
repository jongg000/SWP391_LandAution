package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.ERole;
import com.se1858.G5.LandAuction.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
    List<Roles> findByRoleNameIn(List<ERole> names);
    Roles findByRoleName(ERole roleName);
    Roles findByRoleID(int roleId);
}
