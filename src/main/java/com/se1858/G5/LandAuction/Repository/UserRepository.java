
package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Roles;
import com.se1858.G5.LandAuction.Entity.Status;
import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByNationalID(String nationalID);
    @Query("SELECT u FROM User u ORDER BY u.userId DESC")
    List<User> findTop3UsersByOrderByIdDesc(Pageable pageable);
    List<User> findByStatusAndRole(Status status, Roles role);
    User findByUserId(int userId);
    List<User> findByRole(Roles role);
}
