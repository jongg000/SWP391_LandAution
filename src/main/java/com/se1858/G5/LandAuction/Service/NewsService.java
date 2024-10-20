package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.News;
import java.util.List;
import java.util.Optional;
public interface NewsService {

    News save(News news);

    News findByNewsId(int id);


    List<News> saveAll(List<News> newsList);


    Optional<News> findById(int id);


    boolean existsById(int id);

    // Find all news entities
    Iterable<News> findAll();

    // Find all news entities by a list of IDs
    List<News> findAllById(List<Integer> ids);

    // Count the total number of news entities
    long count();

    // Delete a news entity by its ID
    void deleteById(int id);

    // Delete a single news entity
    void delete(News news);

    // Delete multiple news entities at once
    void deleteAll(List<News> newsList);

    // Delete all news entities
    void deleteAll();
}
