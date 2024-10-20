package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Service.NewsService;

import com.se1858.G5.LandAuction.Entity.News;
import com.se1858.G5.LandAuction.Repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public News save(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News findByNewsId(int id) {
        return newsRepository.findByNewsId(id);
    }

    @Override
    public List<News> saveAll(List<News> newsList) {
        return newsRepository.saveAll(newsList);
    }

    @Override
    public Optional<News> findById(int id) {
        return newsRepository.findById(id);
    }

    @Override
    public boolean existsById(int id) {
        return newsRepository.existsById(id);
    }

    @Override
    public Iterable<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> findAllById(List<Integer> ids) {
        return newsRepository.findAllById(ids);
    }

    @Override
    public long count() {
        return newsRepository.count();
    }

    @Override
    public void deleteById(int id) {
        newsRepository.deleteById(id);
    }

    @Override
    public void delete(News news) {
        newsRepository.delete(news);
    }

    @Override
    public void deleteAll(List<News> newsList) {
        newsRepository.deleteAll(newsList);
    }

    @Override
    public void deleteAll() {
        newsRepository.deleteAll();
    }
}
