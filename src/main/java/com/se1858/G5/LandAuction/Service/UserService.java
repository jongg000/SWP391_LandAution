package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.UserDTO;
import com.se1858.G5.LandAuction.DTO.WishlistDTO;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Entity.Wishlist;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    protected UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .status(user.getStatus())
                .wallet(user.getWallet())
                .nationalID(user.getNationalID())
                .dob(user.getDob())
                .roleId(user.getRole().getRoleId())
                .build();
    }
}
