package com.se1858.G5.LandAuction.chatComponent.user;


import com.se1858.G5.LandAuction.Entity.ERole;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(User users) {
//        users.setStatus(Status.ONLINE);
        userRepository.save(users);
    }

    public void disconnect(User users) {
        var storedUser = userRepository.findByUserName(users.getUserName());
        if (storedUser != null) {
//            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }

    public List<UsersChatDTO> findConnectedUsers(String userName) {
        User u = userRepository.findByUserName(userName);
        if(u.getRole().getRoleName().equals(ERole.ROLE_CUSTOMER_CARE)) {
            return userRepository.findAllOtherCustomerCare();
        } else {
            return userRepository.findAllCustomerCare();
        }
    }
}
