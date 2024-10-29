package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.DTO.UsersChatDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalID(String nationalID);

    @Query("SELECT new com.se1858.G5.LandAuction.DTO.UsersChatDTO(u.email, u.email) " +
            "FROM User u WHERE u.role.roleID != 4")
    List<UsersChatDTO> findAllOtherCustomerCare();

    @Query("SELECT new com.se1858.G5.LandAuction.DTO.UsersChatDTO(u.email, u.email) " +
            "FROM User u WHERE u.role.roleID = 4")
    List<UsersChatDTO> findAllCustomerCare();
}

