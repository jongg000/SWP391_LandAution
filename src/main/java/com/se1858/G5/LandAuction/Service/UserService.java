package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.User;

public interface UserService {

    User findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalID(String nationalID);
}
