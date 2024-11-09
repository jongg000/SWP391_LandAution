package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.ProfileDTO;
import com.se1858.G5.LandAuction.DTO.UserRegisterDTO;
import com.se1858.G5.LandAuction.Entity.Roles;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.RolesRepository;
import com.se1858.G5.LandAuction.Repository.StatusRepository;
import com.se1858.G5.LandAuction.Service.UserService;
import com.se1858.G5.LandAuction.util.UploadFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RolesRepository roleRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private UploadFile uploadFile;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Principal principal;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showRegisterForm_ShouldReturnRegisterView() {
        String viewName = userController.showRegisterForm(model);
        assertEquals("register", viewName);
    }

//    @Test
//    void register_ShouldRedirectToLogin_OnSuccess() {
//        // Arrange
//        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
//        userRegisterDTO.setEmail("test@example.com");
//        userRegisterDTO.setPassword("password");
//        userRegisterDTO.setFirstName("First");
//        userRegisterDTO.setLastName("Last");
//        userRegisterDTO.setPhoneNumber("123456789");
//
//        Roles role = new Roles();
//        role.setRoleID(1); // Ensure this ID is valid in your tests
//
//        when(userService.existsByEmail("test@example.com")).thenReturn(false);
//        when(userService.existsByPhoneNumber("123456789")).thenReturn(false);
//        when(roleRepository.findById(1)).thenReturn(Optional.of(role));
//
//        // Act
//        String result = userController.register(userRegisterDTO, null, mock(Model.class));
//
//        // Assert
//        assertEquals("redirect:/login", result);
//    }

//
//    @Test
//    void register_ShouldReturnRegisterView_OnEmailError() {
//        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
//        userRegisterDTO.setEmail("test@example.com");
//
//        when(userService.existsByEmail(userRegisterDTO.getEmail())).thenReturn(true);
//
//        String viewName = userController.register(userRegisterDTO, bindingResult, model);
//        verify(model).addAttribute("emailError", "Email này đã tồn tại.");
//        assertEquals("register", viewName);
//    }

    @Test
    void showProfile_ShouldReturnProfileView() {
        User user = new User();
        user.setEmail("test@example.com");
        ProfileDTO profileDTO = new ProfileDTO();

        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.findByEmail(user.getEmail())).thenReturn(user);

        String viewName = userController.showProfile(model, principal);
        assertEquals("customer/profile", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    void changePassword_ShouldReturnProfileView_OnIncorrectCurrentPassword() {
        User user = new User();
        user.setPassword("encodedCurrentPassword");

        when(principal.getName()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches("incorrectPassword", user.getPassword())).thenReturn(false);

        String viewName = userController.changePassword("incorrectPassword", "newPassword", "confirmPassword", principal, model);
        verify(model).addAttribute("error", "Mật khẩu hiện tại không đúng.");
        assertEquals("customer/profile", viewName);
    }

//    @Test
//    void updateProfile_ShouldRedirectToProfile_OnSuccess() {
//        ProfileDTO profileDTO = new ProfileDTO();
//        profileDTO.setEmail("newemail@example.com");
//
//        User user = new User();
//        user.setEmail("oldemail@example.com");
//
//        when(principal.getName()).thenReturn("oldemail@example.com");
//        when(userService.findByEmail("oldemail@example.com")).thenReturn(user);
//        when(userService.existsByEmail(profileDTO.getEmail())).thenReturn(false);
//        when(userService.existsByPhoneNumber(profileDTO.getPhoneNumber())).thenReturn(false);
//
//        String viewName = userController.updateProfile(profileDTO, principal, model);
//        assertEquals("redirect:/profile", viewName);
//    }
//
//    @Test
//    void updateProfile_ShouldReturnProfileView_OnExistingEmailError() {
//        ProfileDTO profileDTO = new ProfileDTO();
//        profileDTO.setEmail("existingemail@example.com");
//
//        User user = new User();
//        user.setEmail("user@example.com");
//
//        when(principal.getName()).thenReturn("user@example.com");
//        when(userService.findByEmail("user@example.com")).thenReturn(user);
//        when(userService.existsByEmail(profileDTO.getEmail())).thenReturn(true);
//
//        String viewName = userController.updateProfile(profileDTO, principal, model);
//        verify(model).addAttribute("emailError", "Email này đã tồn tại.");
//        assertEquals("customer/profile", viewName);
//    }
//
//    @Test
//    void upAvatar_ShouldRedirectToProfile_OnSuccess() throws Exception {
//        // Arrange
//        MultipartFile multipartFile = mock(MultipartFile.class);
//        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
//        Model model = mock(Model.class);
//        Principal principal = mock(Principal.class);
//
//        User user = new User();
//        user.setEmail("test@example.com");
//
//        when(principal.getName()).thenReturn("test@example.com");
//        when(userService.findByEmail("test@example.com")).thenReturn(user);
//        doNothing().when(uploadFile).upLoadDocumentAvata(any(MultipartFile.class), any(User.class));
//
//        // Act
//        String viewName = userController.upAvatar(multipartFile, redirectAttributes, principal, model);
//
//        // Assert
//        verify(uploadFile, times(1)).upLoadDocumentAvata(multipartFile, user);
//        assertEquals("redirect:/profile", viewName);
//    }
//
//
//    @Test
//    void upNation_ShouldRedirectToProfile_OnSuccess() throws IOException {
//        User user = new User();
//        user.setEmail("test@example.com");
//
//        when(principal.getName()).thenReturn("test@example.com");
//        when(userService.findByEmail("test@example.com")).thenReturn(user);
//
//        String viewName = userController.upNation(multipartFile, multipartFile, redirectAttributes, principal, model);
//        verify(uploadFile).UploadImagesNationalF(multipartFile, user);
//        verify(uploadFile).UploadImagesNationalB(multipartFile, user);
//        verify(userService).save(user);
//        assertEquals("redirect:/profile", viewName);
//    }
}
