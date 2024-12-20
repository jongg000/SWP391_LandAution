package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsService {
     News save(News news);
     List<News> getAllNews();
     News getNewsById(int id);
     void deleteNewsById(int id);
     List<News> findTop4ByOrderByNewsIdDesc();
     List<News> findByTitle(String title);
     public Page<News> getNewsByUser(User user, int page, int size);
     public Page<News> getAllNews(int page, int size);
     public Page<News> getAllNews(Pageable pageable);
     public Page<News> searchAndSortNews(String search, Pageable pageable);
     public Page<News> searchAndSortUserNews(User user, String search, Pageable pageable);


}
