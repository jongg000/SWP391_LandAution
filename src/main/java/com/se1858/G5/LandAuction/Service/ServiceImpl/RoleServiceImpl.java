package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.ERole;
import com.se1858.G5.LandAuction.Entity.Roles;
import com.se1858.G5.LandAuction.Repository.RolesRepository;
import com.se1858.G5.LandAuction.Service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl  implements RoleService {

    private RolesRepository rolesRepository;

    @Override
    public Optional<Roles> findByRoleName(ERole roleName) {
        return rolesRepository.findByRoleName(roleName);
    }
}
