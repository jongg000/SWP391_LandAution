package com.se1858.G5.LandAuction.Repository;

import com.se1858.G5.LandAuction.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}
