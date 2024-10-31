package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.ERole;
import com.se1858.G5.LandAuction.Entity.Roles;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Roles findByRoleName(ERole roleName);
    public List<Roles> findByRoleNameIn(List<ERole> roleNames);

    Roles findByRoleID(int roleId);
}
