package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.Roles;
import com.se1858.G5.LandAuction.Entity.Status;
import com.se1858.G5.LandAuction.Entity.Token;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.TokenRepository;
import com.se1858.G5.LandAuction.Repository.UserRepository;
import com.se1858.G5.LandAuction.Service.ServiceImpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findByEmail_ShouldReturnUser() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        User result = userService.findByEmail("test@example.com");
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void existsByEmail_ShouldReturnTrue() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertTrue(userService.existsByEmail("test@example.com"));
    }

    @Test
    void findByUserId_ShouldReturnUser() {
        User user = new User();
        user.setUserId(1);

        when(userRepository.findByUserId(1)).thenReturn(user);

        User result = userService.findByUserId(1);
        assertEquals(1, result.getUserId());
    }

    @Test
    void findUsersByStatusAndRole_ShouldReturnUserList() {
        Status status = new Status();
        Roles role = new Roles();
        List<User> users = List.of(new User(), new User());

        when(userRepository.findByStatusAndRole(status, role)).thenReturn(users);

        List<User> result = userService.findUsersByStatusAndRole(status, role);
        assertEquals(2, result.size());
    }

    @Test
    void findUsersByRole_ShouldReturnUserList() {
        Roles role = new Roles();
        List<User> users = List.of(new User(), new User());

        when(userRepository.findByRole(role)).thenReturn(users);

        List<User> result = userService.findUsersByRole(role);
        assertEquals(2, result.size());
    }

    @Test
    void save_ShouldReturnSavedUser() {
        User user = new User();

        when(userRepository.save(user)).thenReturn(user);

        User result = userService.save(user);
        assertNotNull(result);
    }

    @Test
    void existsByPhoneNumber_ShouldReturnTrue() {
        when(userRepository.existsByPhoneNumber("123456789")).thenReturn(true);

        assertTrue(userService.existsByPhoneNumber("123456789"));
    }

    @Test
    void existsByNationalID_ShouldReturnTrue() {
        when(userRepository.existsByNationalID("ID123")).thenReturn(true);

        assertTrue(userService.existsByNationalID("ID123"));
    }

    @Test
    void encodePassword_ShouldReturnEncodedPassword() {
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        assertEquals(encodedPassword, userService.encodePassword(rawPassword));
    }

    @Test
    void createPasswordResetToken_ShouldReturnToken() {
        User user = new User();
        String tokenValue = UUID.randomUUID().toString();

        when(tokenRepository.save(any(Token.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String token = userService.createPasswordResetToken(user);
        assertNotNull(token);
    }

    @Test
    void getTotalUsers_ShouldReturnCount() {
        when(userRepository.count()).thenReturn(100L);

        assertEquals(100, userService.getTotalUsers());
    }

    @Test
    void findTop3UsersByOrderByIdDesc_ShouldReturnUserList() {
        List<User> users = List.of(new User(), new User(), new User());

        when(userRepository.findTop3UsersByOrderByIdDesc(PageRequest.of(0, 3))).thenReturn(users);

        List<User> result = userService.findTop3UsersByOrderByIdDesc();
        assertEquals(3, result.size());
    }

    @Test
    void findAll_ShouldReturnUserList() {
        List<User> users = List.of(new User(), new User());

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void findUsersById_ShouldReturnUserPage() {
        User user = new User();
        user.setUserId(1);
        List<User> users = List.of(user);
        Page<User> userPage = new PageImpl<>(users);

        when(userRepository.findByUserId(1, PageRequest.of(0, 1))).thenReturn(userPage);

        Page<User> result = userService.findUsersById(1, PageRequest.of(0, 1));
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findUsersByRoleExcluding_ShouldReturnUserPage() {
        List<User> users = List.of(new User(), new User());
        Page<User> userPage = new PageImpl<>(users);

        when(userRepository.findByRole_RoleIDNot(1, PageRequest.of(0, 2))).thenReturn(userPage);

        Page<User> result = userService.findUsersByRoleExcluding(PageRequest.of(0, 2), 1);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void findUsersByStatuses_ShouldReturnUserList() {
        Status status1 = new Status();
        Status status2 = new Status();
        List<Status> statuses = List.of(status1, status2);
        List<User> users = List.of(new User(), new User());

        when(userRepository.findByStatusIn(statuses)).thenReturn(users);

        List<User> result = userService.findUsersByStatuses(statuses);
        assertEquals(2, result.size());
    }
}
