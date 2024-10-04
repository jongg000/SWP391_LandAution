package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = userRepository.findByUserName(username);

        return (UserDetails) userDetail.orElseThrow(() -> new UsernameNotFoundException(
                "User not found with username: " + username));
    }
}
