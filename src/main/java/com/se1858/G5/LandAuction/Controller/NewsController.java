package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Entity.Roles;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.ModelMap;


import com.se1858.G5.LandAuction.Service.NewsService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;


@Controller
@RequestMapping("/news")
public class NewsController {
    private static final String UPLOAD_DIR = "uploads/";
    @Autowired
    private NewsService newsService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext servletContext;
    @RequestMapping("/newslist")
    public String listNews(ModelMap model,@RequestParam(value = "s", required = false) String s ) {
        if(s == null || s.equals("")) {
            model.addAttribute("LIST_NEWS", newsService.findAll());
        } else {
            model.addAttribute("LIST_NEWS", newsService.findAllByTitle(s));
        }
//        model.addAttribute("LIST_NEWS", newsService.findAll());
        return "list-news";
    }

    @GetMapping("/detail")
    public String getNews(@RequestParam("id") Integer id, ModelMap model) {
        News news = newsService.findByNewsId(id);
        model.addAttribute("news", news);
        return "news-detail";
    }

    @GetMapping("/new")
    public String showCreateNewsForm(
            @RequestParam(value = "id", required = false) Integer id,
            ModelMap model) {
        News news;

        if (id != null) {
            // Giả sử bạn có một service để lấy News từ DB theo ID
            news = newsService.findByNewsId(id);
        } else {
            news = new News(); // Nếu không có ID, tạo mới News
        }
        model.addAttribute("news", news);
        User u = new User();
//

        u.setRole(new Roles());
//
        model.addAttribute("users", Arrays.asList(u));
        return "add-news";
    }

    // Xử lý submit form với file upload
    @PostMapping("/save")
    public String saveNews(@ModelAttribute("news") News news,
                           @RequestParam("imageFile") MultipartFile imageFile, HttpSession session) {
        // Xử lý upload file
        if (!imageFile.isEmpty()) {
            try {
                byte[] imageBytes = imageFile.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                news.setImage(base64Image);

            } catch (IOException e) {
                e.printStackTrace(); // Bắt lỗi nếu có vấn đề trong quá trình upload
            }
        }

        // Lưu news vào cơ sở dữ liệu
//        news.setUser(userService.findByUserName("tung"));
        String username = (String) session.getAttribute("username");
        User u = userService.findByEmail(username);
        news.setUser(u);
        newsService.save(news);
        return "redirect:/news/newslist";
    }

    @DeleteMapping("/{id}")
    public String deleteNews(@PathVariable("id") int id){
        newsService.deleteById(id);
        return "redirect:/news/newslist";
    }

    private String getFileExtension(String fileName) {
        return fileName != null && fileName.contains(".") ?
                fileName.substring(fileName.lastIndexOf(".")) : "";
    }

}