package com.se1858.G5.LandAuction.Service.ServiceImpl;


import com.se1858.G5.LandAuction.Entity.Roles;
import com.se1858.G5.LandAuction.Entity.Status;
import com.se1858.G5.LandAuction.Entity.Token;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.TokenRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    public User findByUserId(int userId){
        return userRepository.findByUserId(userId);
    }

    @Override
    public List<User> findUsersByStatusAndRole(Status status, Roles role) {
        return userRepository.findByStatusAndRole(status, role);
    }

    @Override
    public List<User> findUsersByRole(Roles role) {
        return userRepository.findByRole(role);
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
    public long getTotalUsers() {
        return userRepository.count();
    }

    @Override
    public List<User> findTop3UsersByOrderByIdDesc() {
        return userRepository.findTop3UsersByOrderByIdDesc(PageRequest.of(0,3));
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Page<User> findUsersById(int userId, PageRequest pageRequest) {
        return userRepository.findByUserId(userId, pageRequest);
    }

    public Page<User> findUsersByRoleExcluding(PageRequest pageRequest, int excludedRoleId) {
        return userRepository.findByRole_RoleIDNot(excludedRoleId, pageRequest);
    }

}
