package com.se1858.G5.LandAuction.Security;

import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Exception.InvalidEmailException;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws InvalidEmailException {
        User user = userRepository.findByUserName(username);
        if (user == null || !user.getUserName().equalsIgnoreCase(username)) {
            throw new InvalidEmailException("Email is not valid");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName().name().toUpperCase());
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), List.of(authority));
    }
}