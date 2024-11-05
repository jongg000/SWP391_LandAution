package com.se1858.G5.LandAuction.Controller;


import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Service.NewsService;
import com.se1858.G5.LandAuction.util.UploadFile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private UploadFile uploadFile;

    @GetMapping
    public String news(Model model, @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "6") int size, // Đặt size mặc định là 6
                       @RequestParam(defaultValue = "desc") String sort) {
        Sort sortOrder = sort.equals("latest") ? Sort.by("newsId").descending() : Sort.by("newsId").ascending();
        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // Lấy danh sách tin tức từ service
        Page<News> newsPage = newsService.getAllNews(pageable);
        List<News> list = newsService.getAllNews();
        model.addAttribute("news", newsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", newsPage.getTotalPages());
        model.addAttribute("sortOrder", sort);
        return "news";
    }

    @GetMapping("/search")
    public String searchNewsByTitle(@RequestParam("title") String title, Model model) {
        List<News> searchResults = newsService.findByTitle(title);
        model.addAttribute("news", searchResults);
        return "news"; // Name of the view for displaying search results
    }

    @GetMapping("/newsDetail")
    public String getNewsById(@RequestParam("newsId") int newsId, Model model) {
        News news = newsService.getNewsById(newsId);
        model.addAttribute("news", news);
        return "news-detail";
    }

}
