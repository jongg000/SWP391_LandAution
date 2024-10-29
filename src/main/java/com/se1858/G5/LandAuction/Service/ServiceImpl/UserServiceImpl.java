package com.se1858.G5.LandAuction.Service.ServiceImpl;


import com.se1858.G5.LandAuction.DTO.UsersChatDTO;
import com.se1858.G5.LandAuction.Entity.ERole;
import com.se1858.G5.LandAuction.Entity.Token;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.TokenRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private TokenRepository tokenRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
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
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByNationalID(String nationalID) {
        return userRepository.existsByNationalID(nationalID);
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public String createPasswordResetToken(User user) {
        String token = UUID.randomUUID().toString();
        Token resetToken = new Token();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600 * 1000)); // Hết hạn sau 1 giờ
        tokenRepository.save(resetToken);
        return token;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void disconnect(User users) {
        var storedUser = userRepository.findByEmail(users.getEmail());
        if (storedUser != null) {
//            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    @Override
    public List<UsersChatDTO> findConnectedUsers(String email) {
        User u = userRepository.findByEmail(email);
        if(u.getRole().getRoleName().equals(ERole.ROLE_CUSTOMER_CARE)) {
            return userRepository.findAllOtherCustomerCare();
        } else {
            return userRepository.findAllCustomerCare();
        }
    }
}
