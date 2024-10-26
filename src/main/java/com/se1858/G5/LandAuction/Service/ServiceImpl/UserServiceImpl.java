package com.se1858.G5.LandAuction.Service.ServiceImpl;


import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    @Override
    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }
    @Override
    public boolean existsByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }


}
