package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.chatComponent.user.UsersChatDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String username);
    User findByEmail(String email);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);

    @Query("SELECT new com.se1858.G5.LandAuction.chatComponent.user.UsersChatDTO(u.userName, u.name) " +
            "FROM User u WHERE u.role.roleID != 4")
    List<UsersChatDTO> findAllOtherCustomerCare();

    @Query("SELECT new com.se1858.G5.LandAuction.chatComponent.user.UsersChatDTO(u.userName, u.name) " +
            "FROM User u WHERE u.role.roleID = 4")
    List<UsersChatDTO> findAllCustomerCare();
}

