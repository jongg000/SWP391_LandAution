package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.DTO.NewsDTO;
import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Service.NewsService;
import com.se1858.G5.LandAuction.Service.ServiceImpl.UploadFile;
import com.se1858.G5.LandAuction.Service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Controller
@RequestMapping("/customer-care")
@RequiredArgsConstructor
public class CustomerCareController {

    private final NewsService newsService;
    private final UserService userService;

    @GetMapping
    public String news(Model model) {
        List<News> list = newsService.getAllNews();
        model.addAttribute("newsList", list);
        return "customer-care/manage-news";
    }

    @GetMapping("/own-news")
    public String getOwnNews(Model model) {
        List<News> list = newsService.getAllNews();
        model.addAttribute("listNews", list);
        model.addAttribute("pageTitle", "View my own news");
        model.addAttribute("deletePermission", "true");
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
        News news = newsDTO.getNews();
        String email = principal.getName();
        User user = userService.findByEmail(email);
        news.setUser(user);
        news.setTime(LocalDateTime.now());
        UploadFile uploadFile = new UploadFile();
        uploadFile.upLoadImageForNews(images, news);
        newsService.save(news);
        redirectAttributes.addAttribute("message", "The article was created successfully");
        return "customer-care/add-news";
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


}
