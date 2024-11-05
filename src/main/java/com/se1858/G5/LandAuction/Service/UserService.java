package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import java.awt.print.Pageable;
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
    public long getTotalUsers();
    public List<User> findTop3UsersByOrderByIdDesc();
    User findByUserId(int userId);
    Page<User> findUsersById(int userId, PageRequest pageRequest);
    Page<User> findUsersByRoleExcluding(PageRequest pageRequest, int excludedRoleId);
}
