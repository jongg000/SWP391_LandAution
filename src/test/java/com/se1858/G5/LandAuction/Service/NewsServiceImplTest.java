package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.NewsRepository;
import com.se1858.G5.LandAuction.Service.ServiceImpl.NewsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsServiceImpl newsService;

    private News news;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1); // Set appropriate user ID

        news = new News();
        news.setNewsId(1); // Set appropriate news ID
        news.setTitle("Sample News");
        news.setContent("Content of the sample news");
        news.setUser(user); // Assuming News has a User field
    }

    @Test
    void save_ShouldReturnSavedNews() {
        when(newsRepository.save(any(News.class))).thenReturn(news);

        News savedNews = newsService.save(news);

        assertNotNull(savedNews);
        assertEquals("Sample News", savedNews.getTitle());
        verify(newsRepository, times(1)).save(news);
    }

    @Test
    void getAllNews_ShouldReturnListOfNews() {
        when(newsRepository.findAll()).thenReturn(Arrays.asList(news));

        List<News> newsList = newsService.getAllNews();

        assertFalse(newsList.isEmpty());
        assertEquals(1, newsList.size());
        verify(newsRepository, times(1)).findAll();
    }

    @Test
    void getNewsById_ShouldReturnNews() {
        when(newsRepository.getById(1)).thenReturn(news);

        News foundNews = newsService.getNewsById(1);

        assertNotNull(foundNews);
        assertEquals("Sample News", foundNews.getTitle());
        verify(newsRepository, times(1)).getById(1);
    }

    @Test
    void deleteNewsById_ShouldInvokeDelete() {
        doNothing().when(newsRepository).deleteById(1);

        newsService.deleteNewsById(1);

        verify(newsRepository, times(1)).deleteById(1);
    }

    @Test
    void findTop4ByOrderByNewsIdDesc_ShouldReturnTop4News() {
        when(newsRepository.findTop4ByOrderByNewsIdDesc()).thenReturn(Arrays.asList(news));

        List<News> topNews = newsService.findTop4ByOrderByNewsIdDesc();

        assertNotNull(topNews);
        assertEquals(1, topNews.size());
        verify(newsRepository, times(1)).findTop4ByOrderByNewsIdDesc();
    }

    @Test
    void findByTitle_ShouldReturnListOfNews() {
        when(newsRepository.findByTitle("Sample News")).thenReturn(Arrays.asList(news));

        List<News> foundNews = newsService.findByTitle("Sample News");

        assertNotNull(foundNews);
        assertEquals(1, foundNews.size());
        assertEquals("Sample News", foundNews.get(0).getTitle());
        verify(newsRepository, times(1)).findByTitle("Sample News");
    }

    @Test
    void getNewsByUser_ShouldReturnPageOfNews() {
        Page<News> newsPage = new PageImpl<>(Arrays.asList(news));
        when(newsRepository.getNewsByUser(any(User.class), any(PageRequest.class))).thenReturn(newsPage);

        Page<News> resultPage = newsService.getNewsByUser(user, 0, 1);

        assertEquals(1, resultPage.getContent().size());
        verify(newsRepository, times(1)).getNewsByUser(user, PageRequest.of(0, 1));
    }

    @Test
    void getAllNews_WithPagination_ShouldReturnPageOfNews() {
        Page<News> newsPage = new PageImpl<>(Arrays.asList(news));
        when(newsRepository.findAll(any(PageRequest.class))).thenReturn(newsPage);

        Page<News> resultPage = newsService.getAllNews(0, 1);

        assertEquals(1, resultPage.getContent().size());
        verify(newsRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void searchAndSortNews_ShouldReturnFilteredNews() {
        // Giả lập kết quả tìm kiếm
        List<News> newsList = Arrays.asList(new News(), new News());
        Page<News> newsPage = new PageImpl<>(newsList);

        // Sử dụng matchers cho tất cả các tham số
        when(newsRepository.findByTitleContainingIgnoreCase(eq("searchTerm"), any(Pageable.class)))
                .thenReturn(newsPage);

        Page<News> resultPage = newsService.searchAndSortNews("searchTerm", PageRequest.of(0, 10));

        assertEquals(2, resultPage.getContent().size());
        verify(newsRepository, times(1)).findByTitleContainingIgnoreCase(eq("searchTerm"), any(Pageable.class));
    }


    @Test
    void searchAndSortNews_EmptySearch_ShouldReturnAllNews() {
        Page<News> newsPage = new PageImpl<>(Arrays.asList(news));
        when(newsRepository.findAll(any(PageRequest.class))).thenReturn(newsPage);

        Page<News> resultPage = newsService.searchAndSortNews("", PageRequest.of(0, 1));

        assertEquals(1, resultPage.getContent().size());
        verify(newsRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void searchAndSortUserNews_ShouldReturnFilteredUserNews() {
        Page<News> newsPage = new PageImpl<>(Arrays.asList(news));

        // Thay đổi từ any(User.class) thành any() cho user, và dùng eq() cho search
        when(newsRepository.findByUserAndTitleContainingIgnoreCase(any(User.class), any(String.class), any(PageRequest.class)))
                .thenReturn(newsPage);

        // Gọi phương thức với các giá trị tương ứng
        Page<News> resultPage = newsService.searchAndSortUserNews(user, "Sample", PageRequest.of(0, 1));

        assertEquals(1, resultPage.getContent().size());
        verify(newsRepository, times(1)).findByUserAndTitleContainingIgnoreCase(any(User.class), eq("Sample"), any(PageRequest.class));
    }


    @Test
    void searchAndSortUserNews_EmptySearch_ShouldReturnUserNews() {
        Page<News> newsPage = new PageImpl<>(Arrays.asList(news));

        // Sử dụng matchers cho tất cả các tham số
        when(newsRepository.findByUser(any(User.class), any(PageRequest.class)))
                .thenReturn(newsPage);

        Page<News> resultPage = newsService.searchAndSortUserNews(user, "", PageRequest.of(0, 1));

        assertEquals(1, resultPage.getContent().size());
        verify(newsRepository, times(1)).findByUser(any(User.class), any(PageRequest.class));
    }

}
