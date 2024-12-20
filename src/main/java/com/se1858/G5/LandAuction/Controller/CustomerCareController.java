package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.DTO.NewsDTO;
import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Service.NewsService;
import com.se1858.G5.LandAuction.util.UploadFile;
import com.se1858.G5.LandAuction.Service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Controller
@RequestMapping("/customer-care")
@RequiredArgsConstructor
public class CustomerCareController {

    private final NewsService newsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String news(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "8") int size,
                       @RequestParam(defaultValue = "") String search,
                       @RequestParam(defaultValue = "latest") String sort,
                       Model model) {
        Sort sortOrder = sort.equals("oldest") ? Sort.by("newsId").ascending() : Sort.by("newsId").descending();
        Page<News> newsPage = newsService.searchAndSortNews(search, PageRequest.of(page, size, sortOrder));

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", newsPage.getTotalPages());
        model.addAttribute("newsList", newsPage.getContent());
        model.addAttribute("pageUrl", "/customer-care");
        model.addAttribute("sortOrder", sort);
        model.addAttribute("search", search);
        return "customer-care/manage-news";
    }

    @GetMapping("/own-news")
    public String getOwnNews(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "8") int size,
                             @RequestParam(defaultValue = "") String search,
                             @RequestParam(defaultValue = "latest") String sort,
                             Model model,Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        Sort sortOrder = sort.equals("oldest") ? Sort.by("newsId").ascending() : Sort.by("newsId").descending();
        Page<News> newsPage = newsService.searchAndSortUserNews(user, search, PageRequest.of(page, size, sortOrder));

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", newsPage.getTotalPages());
        model.addAttribute("newsList", newsPage.getContent());
        model.addAttribute("pageUrl", "/customer-care/own-news");
        model.addAttribute("sortOrder", sort);
        model.addAttribute("search", search);
        return "customer-care/manage-news";
    }

    @GetMapping("/add")
    public String createNews(Model model) {
        model.addAttribute("newsDTO", new NewsDTO());
        return "customer-care/add-news";
    }

    @PostMapping("/add")
    public String addNews(@ModelAttribute("newsDTO") NewsDTO newsDTO,
                          @RequestParam("images") MultipartFile images,
                          RedirectAttributes redirectAttributes, Principal principal) {
        News news = new News();
        String email = principal.getName();
        User user = userService.findByEmail(email);
        news.setUser(user);
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        // Định dạng thời gian hiện tại thành chuỗi theo định dạng mong muốn
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTime = LocalDateTime.now().format(formatter);
        news.setTime(formattedTime);
        UploadFile uploadFile = new UploadFile();
        uploadFile.upLoadImageForNews(images, news);
        newsService.save(news);
        redirectAttributes.addAttribute("message", "The article was created successfully");
        return "customer-care/add-news";
    }

    @PostMapping("/upload")
    public String upAvatar(@RequestParam("avatar") MultipartFile images,
                          RedirectAttributes redirectAttributes, Principal principal,Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        UploadFile uploadFile = new UploadFile();
        uploadFile.upLoadDocumentAvata(images, user);
        userService.save(user);
        model.addAttribute("user", user);
        return "redirect:/customer-care/profile";
    }

    @GetMapping("/deleteNews")
    public String deleteNews(@RequestParam("newsId") int newsId) {
        newsService.deleteNewsById(newsId);
        return "redirect:/customer-care";
    }


    @GetMapping("/newsDetail")
    public String getNewsById(@RequestParam("newsId") int newsId, Model model) {
        News news = newsService.getNewsById(newsId);
        model.addAttribute("news", news);
        return "customer-care/news-detail";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // Lấy thông tin người dùng hiện tại từ tên đăng nhập (email)
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Đưa thông tin người dùng vào model
        model.addAttribute("user", user);

        // Trả về trang profile
        return "customer-care/profile";
    }

    @GetMapping("/change")
    public String showChangePasswordForm(Model model, Principal principal) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Đưa thông tin người dùng vào model (nếu cần)
        model.addAttribute("user", user);

        return "customer-care/change";
    }


    @PostMapping("/change")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);

        // Kiểm tra mật khẩu hiện tại có đúng không
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng.");
            model.addAttribute("user", user);
            return "customer-care/change";
        }

        // Kiểm tra nếu mật khẩu mới giống với mật khẩu hiện tại
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu mới không được giống mật khẩu hiện tại.");
            model.addAttribute("user", user);
            return "customer-care/change";
        }

        // Kiểm tra xem mật khẩu mới có khớp với xác nhận mật khẩu không
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
            model.addAttribute("user", user);
            return "customer-care/change";
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);

        model.addAttribute("success", "Mật khẩu đã được cập nhập.");
        model.addAttribute("user", user);
        return "customer-care/change";
    }


}
