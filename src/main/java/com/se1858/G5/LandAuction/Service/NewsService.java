package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.News;

import java.util.List;

public interface NewsService {
     News save(News news);
     List<News> getAllNews();
     News getNewsById(int id);
     void deleteNewsById(int id);
     List<News> findTop4ByOrderByTimeDesc();
     List<News> findByTitle(String title);

}
