package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.ERole;
import com.se1858.G5.LandAuction.Entity.Roles;

import java.util.Optional;

public interface RoleService {
    Optional<Roles> findByRoleName(ERole roleName);
}
