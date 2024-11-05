package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Entity.User;
import com.se1858.G5.LandAuction.Repository.NewsRepository;
import com.se1858.G5.LandAuction.Service.NewsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsServiceImpl  implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News getNewsById(int id) {
        return newsRepository.getById(id);
    }

    @Override
    public void deleteNewsById(int id) {
        newsRepository.deleteById(id);
    }

    @Override
    public List<News> findTop4ByOrderByTimeDesc() {
        return newsRepository.findTop4ByOrderByTimeDesc();
    }

    @Override
    public List<News> findByTitle(String title) {
        return newsRepository.findByTitle(title);
    }

    @Override
    public Page<News> getNewsByUser(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepository.getNewsByUser(user, pageable);
    }

    @Override
    public Page<News> getAllNews(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return newsRepository.findAll(pageable);
    }
}
