package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String username);
    User findByEmail(String email);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);

}

