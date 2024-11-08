package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.DTO.NewsDTO;
import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Service.NewsService;
import com.se1858.G5.LandAuction.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CustomerCareControllerTest {

    @InjectMocks
    private CustomerCareController customerCareController;

    @Mock
    private NewsService newsService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @Mock
    private Principal principal;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void news_ShouldReturnManageNewsView_WhenNewsExists() {
        // Giả định có 4 tin tức với 2 trang
        News news1 = new News(1, "Title 1", "Content 1", null, null, null);
        News news2 = new News(2, "Title 2", "Content 2", null, null, null);
        News news3 = new News(3, "Title 3", "Content 3", null, null, null);
        News news4 = new News(4, "Title 4", "Content 4", null, null, null);

        List<News> newsList = Arrays.asList(news1, news2, news3, news4);
        Page<News> newsPage = new PageImpl<>(newsList.subList(0, 2), PageRequest.of(0, 2), newsList.size());

        when(newsService.searchAndSortNews(anyString(), any())).thenReturn(newsPage);

        String viewName = customerCareController.news(0, 2, "", "latest", model);

        assertEquals("customer-care/manage-news", viewName);
        verify(model).addAttribute("currentPage", 0);
        verify(model).addAttribute("totalPages", 2); // Chỉnh sửa này
        verify(model).addAttribute("newsList", Arrays.asList(news1, news2));
        verify(model).addAttribute("pageUrl", "/customer-care");
        verify(model).addAttribute("sortOrder", "latest");
        verify(model).addAttribute("search", "");
    }


    @Test
    public void news_ShouldReturnManageNewsView_WhenNoNewsExists() {
        Page<News> newsPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(0, 8), 0);

        when(newsService.searchAndSortNews(anyString(), any())).thenReturn(newsPage);

        String viewName = customerCareController.news(0, 8, "", "latest", model);

        assertEquals("customer-care/manage-news", viewName);
        verify(model).addAttribute("currentPage", 0);
        verify(model).addAttribute("totalPages", 0);
        verify(model).addAttribute("newsList", Collections.emptyList());
        verify(model).addAttribute("pageUrl", "/customer-care");
        verify(model).addAttribute("sortOrder", "latest");
        verify(model).addAttribute("search", "");
    }

    @Test
    public void getOwnNews_ShouldReturnOwnNewsView_WhenNewsExists() {
        User user = new User();
        user.setEmail("test@example.com");
        News news1 = new News(1, "My News 1", "Content 1", null, user, null);
        Page<News> newsPage = new PageImpl<>(Collections.singletonList(news1), PageRequest.of(0, 8), 1);

        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.findByEmail(user.getEmail())).thenReturn(user);
        when(newsService.searchAndSortUserNews(eq(user), anyString(), any())).thenReturn(newsPage);

        String viewName = customerCareController.getOwnNews(0, 8, "", "latest", model, principal);

        assertEquals("customer-care/manage-news", viewName);
        verify(model).addAttribute("currentPage", 0);
        verify(model).addAttribute("totalPages", 1);
        verify(model).addAttribute("newsList", Collections.singletonList(news1));
        verify(model).addAttribute("pageUrl", "/customer-care/own-news");
        verify(model).addAttribute("sortOrder", "latest");
        verify(model).addAttribute("search", "");
    }

    @Test
    public void createNews_ShouldReturnAddNewsView() {
        String viewName = customerCareController.createNews(model);

        assertEquals("customer-care/add-news", viewName);
        verify(model).addAttribute("newsDTO", new NewsDTO());
    }

    @Test
    public void addNews_ShouldRedirectToAddNews_WhenSuccess() {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setTitle("New Title");
        newsDTO.setContent("New Content");
        MultipartFile mockFile = mock(MultipartFile.class);
        User user = new User();
        user.setEmail("test@example.com");

        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.findByEmail(user.getEmail())).thenReturn(user);

        String viewName = customerCareController.addNews(newsDTO, mockFile, redirectAttributes, principal);

        assertEquals("customer-care/add-news", viewName);
        verify(redirectAttributes).addAttribute("message", "The article was created successfully");
    }

    @Test
    public void deleteNews_ShouldRedirectToCustomerCare() {
        String viewName = customerCareController.deleteNews(1);

        assertEquals("redirect:/customer-care", viewName);
        verify(newsService).deleteNewsById(1);
    }

    @Test
    public void getNewsById_ShouldReturnNewsDetailView() {
        News news = new News(1, "Detail Title", "Detail Content", null, null, null);

        when(newsService.getNewsById(1)).thenReturn(news);

        String viewName = customerCareController.getNewsById(1, model);

        assertEquals("customer-care/news-detail", viewName);
        verify(model).addAttribute("news", news);
    }

    @Test
    public void showProfile_ShouldReturnProfileView() {
        User user = new User();
        user.setEmail("test@example.com");

        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.findByEmail(user.getEmail())).thenReturn(user);

        String viewName = customerCareController.showProfile(model, principal);

        assertEquals("customer-care/profile", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    public void showChangePasswordForm_ShouldReturnChangePasswordView() {
        User user = new User();
        user.setEmail("test@example.com");

        when(principal.getName()).thenReturn(user.getEmail());
        when(userService.findByEmail(user.getEmail())).thenReturn(user);

        String viewName = customerCareController.showChangePasswordForm(model, principal);

        assertEquals("customer-care/change", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    public void changePassword_ShouldRedirectToChange_WhenCurrentPasswordIsWrong() {
        String currentPassword = "wrongPassword";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";
        User user = new User();
        user.setPassword("correctPassword");

        when(principal.getName()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches(currentPassword, user.getPassword())).thenReturn(false);

        String viewName = customerCareController.changePassword(currentPassword, newPassword, confirmPassword, principal, model);

        assertEquals("customer-care/change", viewName);
        verify(model).addAttribute("error", "Mật khẩu hiện tại không đúng.");
        verify(model).addAttribute("user", user);
    }

    @Test
    public void changePassword_ShouldRedirectToChange_WhenNewPasswordIsSameAsCurrent() {
        String currentPassword = "correctPassword";
        String newPassword = "correctPassword";
        String confirmPassword = "newPassword";
        User user = new User();
        user.setPassword("correctPassword");

        when(principal.getName()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches(currentPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(true);

        String viewName = customerCareController.changePassword(currentPassword, newPassword, confirmPassword, principal, model);

        assertEquals("customer-care/change", viewName);
        verify(model).addAttribute("error", "Mật khẩu mới không được giống mật khẩu hiện tại.");
        verify(model).addAttribute("user", user);
    }

    @Test
    public void changePassword_ShouldRedirectToChange_WhenPasswordsDoNotMatch() {
        String currentPassword = "correctPassword";
        String newPassword = "newPassword";
        String confirmPassword = "differentPassword";
        User user = new User();
        user.setPassword("correctPassword");

        when(principal.getName()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches(currentPassword, user.getPassword())).thenReturn(true);

        String viewName = customerCareController.changePassword(currentPassword, newPassword, confirmPassword, principal, model);

        assertEquals("customer-care/change", viewName);
        verify(model).addAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
        verify(model).addAttribute("user", user);
    }

    @Test
    public void changePassword_ShouldRedirectToChange_WhenSuccess() {
        String currentPassword = "correctPassword";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";
        User user = new User();
        user.setPassword("correctPassword");

        when(principal.getName()).thenReturn("test@example.com");
        when(userService.findByEmail("test@example.com")).thenReturn(user);
        when(passwordEncoder.matches(currentPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(newPassword, user.getPassword())).thenReturn(false);

        String viewName = customerCareController.changePassword(currentPassword, newPassword, confirmPassword, principal, model);

        assertEquals("customer-care/change", viewName);
        verify(model).addAttribute("success", "Mật khẩu đã được cập nhập.");
        verify(model).addAttribute("user", user);
        verify(userService).save(user);
    }
}
