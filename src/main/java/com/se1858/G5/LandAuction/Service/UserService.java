package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.UsersChatDTO;
import com.se1858.G5.LandAuction.Entity.User;

import java.util.List;

public interface UserService {

    User findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalID(String nationalID);
    String encodePassword(String password);
    String createPasswordResetToken(User user);

    List<User> findAll();

    void disconnect(User users);
    List<UsersChatDTO> findConnectedUsers(String email);
}
