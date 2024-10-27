package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.UserRegisterDTO;
import com.se1858.G5.LandAuction.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class UserControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController; // Đối tượng controller cần kiểm tra

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Khởi tạo các mock
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Khởi tạo MockMvc
    }

    @Test
    void register_ShouldRedirectToLogin_WhenSuccess() throws Exception {
        // Giả lập email và số điện thoại không tồn tại
        when(userService.existsByEmail(anyString())).thenReturn(false);
        when(userService.existsByPhoneNumber(anyString())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("email", "test@example.com")
                        .param("phoneNumber", "08012345678")
                        .param("password", "password123")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())  // Kiểm tra chuyển hướng
                .andExpect(MockMvcResultMatchers.view().name("redirect:/login"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("success"));
    }

}
