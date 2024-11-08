
package com.se1858.G5.LandAuction.Security;

import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.getEmail().equalsIgnoreCase(email)) {
            throw new UsernameNotFoundException("Email is not valid");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new BadCredentialsException("Password cannot be null or empty");
        }
        // Kiểm tra nếu trạng thái của người dùng là 3
        if (user.getStatus().getStatusID() == 3) {
            throw new DisabledException("This account is disabled");
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleName().name().toUpperCase());

        // Return the hashed password from the database
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), List.of(authority));
    }


}
