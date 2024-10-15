package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.User;

public interface UserService {
    User findByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    User save(User user);
}
