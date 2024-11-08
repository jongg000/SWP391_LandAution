package com.se1858.G5.LandAuction.Controller;

import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NewsControllerTest {

    @InjectMocks
    private NewsController newsController;

    @Mock
    private NewsService newsService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void news_ShouldReturnNewsPage() {
        // Arrange
        int page = 0;
        int size = 6;
        Sort sortOrder = Sort.by("newsId").descending();
        PageRequest pageable = PageRequest.of(page, size, sortOrder);

        News news1 = new News();
        news1.setNewsId(1);
        News news2 = new News();
        news2.setNewsId(2);
        List<News> newsList = Arrays.asList(news1, news2);
        Page<News> newsPage = new PageImpl<>(newsList);

        when(newsService.getAllNews(pageable)).thenReturn(newsPage);
        when(newsService.getAllNews()).thenReturn(newsList);

        // Act
        String viewName = newsController.news(model, page, size, "latest");

        // Assert
        assertEquals("news", viewName);
        verify(model).addAttribute("news", newsPage);
        verify(model).addAttribute("currentPage", page);
        verify(model).addAttribute("totalPages", newsPage.getTotalPages());
        verify(model).addAttribute("sortOrder", "latest");
    }

    @Test
    void searchNewsByTitle_ShouldReturnSearchResults() {
        // Arrange
        String title = "Some News Title";
        News news1 = new News();
        news1.setNewsId(1);
        News news2 = new News();
        news2.setNewsId(2);
        List<News> searchResults = Arrays.asList(news1, news2);

        when(newsService.findByTitle(title)).thenReturn(searchResults);

        // Act
        String viewName = newsController.searchNewsByTitle(title, model);

        // Assert
        assertEquals("news", viewName);
        verify(model).addAttribute("news", searchResults);
    }

    @Test
    void getNewsById_ShouldReturnNewsDetail() {
        // Arrange
        int newsId = 1;
        News news = new News();
        news.setNewsId(newsId);

        when(newsService.getNewsById(newsId)).thenReturn(news);

        // Act
        String viewName = newsController.getNewsById(newsId, model);

        // Assert
        assertEquals("news-detail", viewName);
        verify(model).addAttribute("news", news);
    }

    @Test
    void searchAndSortUserNews_NonEmptySearch_ShouldReturnFilteredNews() {
        // Arrange
        User user = new User();
        News news1 = new News(1, "Breaking News", null, null, user, null);
        List<News> newsList = Collections.singletonList(news1);

        when(newsService.findByTitle("Breaking News")).thenReturn(newsList);

        // Act
        String viewName = newsController.searchNewsByTitle("Breaking News", model);

        // Assert
        assertEquals("news", viewName);
        verify(model).addAttribute("news", newsList); // Kiểm tra rằng news đã được thêm vào model
    }

    @Test
    void news_ShouldHandleNoNews() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 6, Sort.by("newsId").descending());
        Page<News> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        // Giả lập để trả về trang trống
        when(newsService.getAllNews(pageable)).thenReturn(emptyPage);

        // Act
        String viewName = newsController.news(model, 0, 6, "latest");

        // Assert
        assertEquals("news", viewName);
        verify(model).addAttribute("news", emptyPage);
        verify(model).addAttribute("currentPage", 0);
        verify(model).addAttribute("totalPages", 0); // Kiểm tra lại tổng số trang
        verify(model).addAttribute("sortOrder", "latest");
    }

}
