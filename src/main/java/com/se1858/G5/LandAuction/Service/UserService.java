package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.DTO.Request.UserRegisterRequest;
import com.se1858.G5.LandAuction.Entity.Roles;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Mapper.UserMapper;
import com.se1858.G5.LandAuction.Repository.RolesRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void registerUser(UserRegisterRequest request) {
        User user = UserMapper.INSTANCE.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Roles roles = rolesRepository.findById(1).orElse(null);
        user.setRole(roles);
        userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }
}
